package engine.game;

import engine.UIToolKit.UIViewport;
import engine.game.components.Component;
import engine.game.components.TransformComponent;
import engine.game.systems.SystemFlag;
import engine.game.systems.*;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GameWorld {

    //TODO handle inputs faster than ticks.
    private Set<KeyCode> keyState;
    private Vec2d mousePosition = new Vec2d(0,0);
    private boolean mouseDown = false;

    private TickSystem tickSystem = new TickSystem();
    private RenderSystem renderSystem = new RenderSystem();
    private CollisionSystem collisionSystem = new CollisionSystem();
    private KeyEventSystem keyEventSystem = new KeyEventSystem();
    private MouseEventSystem mouseEventSystem = new MouseEventSystem();

    private GeneralSystem[] systemsList = {tickSystem, renderSystem, collisionSystem, keyEventSystem, mouseEventSystem};

    private List<GameObject> gameObjects;
    private List<GameObject> addQueue;
    private List<GameObject> removeQueue;

    private Map<Integer, UIViewport> viewportMap = new HashMap<Integer, UIViewport>();


    public GameWorld(){
        keyState = new HashSet<KeyCode>();
        gameObjects = new ArrayList<GameObject>();

        addQueue = new ArrayList<GameObject>();
        removeQueue = new ArrayList<GameObject>();
    }

    public void linkViewport(int id, UIViewport viewport){
        this.viewportMap.put(id,viewport);
    }

    public UIViewport getViewport(int id){
        return this.viewportMap.get(id);
    }

    public void onTick(long nanosSincePreviousTick) {
        while(!addQueue.isEmpty()) {
            this.processGameObject(this.addQueue.remove(0));
        }
        while(!removeQueue.isEmpty()) {
            this.deprocessGameObject(this.removeQueue.remove(0));
        }
        this.tickSystem.onTick(nanosSincePreviousTick);
        this.collisionSystem.onTick(nanosSincePreviousTick);
    }

    public void onLateTick(){
        this.tickSystem.onLateTick();
        this.collisionSystem.onLateTick();

    }

    public void onDraw(GraphicsContext g) {
        this.renderSystem.onDraw(g);
    }

    public void addGameObject(GameObject gameObject){
        this.addQueue.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject){
        this.removeQueue.add(gameObject);
    }

    //TODO rewrite to loop over systems in list
    //Checking cases looks bad
    private void processGameObject(GameObject gameObject){
        this.gameObjects.add(gameObject);
        for(Component c : gameObject.componentList) {
            int flags = c.getSystemFlags();
            for(GeneralSystem system : systemsList){
                if ((flags & system.getSystemFlag()) != 0) {
                    system.addComponent(c);
                }
            }
//            if ((flags & SystemFlag.TickSystem) != 0) {
//                this.tickSystem.addComponent(c);
//            }
//            if ((flags & SystemFlag.RenderSystem) != 0) {
//                this.renderSystem.addComponent(c);
//            }
//            if ((flags & SystemFlag.CollisionSystem) != 0) {
//                this.collisionSystem.addComponent(c);
//            }
//            if ((flags & SystemFlag.KeyEventSystem) != 0) {
//                this.keyEventSystem.addComponent(c);
//            }
//            if ((flags & SystemFlag.MouseEventSystem) != 0) {
//                this.mouseEventSystem.addComponent(c);
//            }
        }
    }
    //TODO rewrite to loop over systems in list
    //Checking cases looks bad
    private void deprocessGameObject(GameObject gameObject){
        for(Component c : gameObject.componentList) {
            int flags = c.getSystemFlags();
            for(GeneralSystem system : systemsList){
                if ((flags & system.getSystemFlag()) != 0) {
                    system.removeComponent(c);
                }
            }
//            if ((flags & SystemFlag.TickSystem) != 0) {
//                this.tickSystem.removeComponent(c);
//            }
//            if ((flags & SystemFlag.RenderSystem) != 0) {
//                this.renderSystem.removeComponent(c);
//            }
//            if ((flags & SystemFlag.CollisionSystem) != 0) {
//                this.collisionSystem.removeComponent(c);
//            }
//            if ((flags & SystemFlag.KeyEventSystem) != 0) {
//                this.keyEventSystem.removeComponent(c);
//            }
//            if ((flags & SystemFlag.MouseEventSystem) != 0) {
//                this.mouseEventSystem.removeComponent(c);
//            }
        }
        this.gameObjects.remove(gameObject);
    }

    public List<GameObject> getGameObjects(){
        return this.gameObjects;
    }

    public void clearAllGameObjects(){
        for (GameObject o : this.gameObjects) {
            this.removeQueue.add(o);
        }
        for (GameObject o : this.removeQueue) {
            this.deprocessGameObject(o);
        }
        this.removeQueue.clear();
        this.gameObjects.clear();
    }

    public Set<KeyCode> getKeyState(){
        return this.keyState;
    }

    public Vec2d getMousePosition(){
        return this.mousePosition;
    }

    public Boolean getMouseDown(){
        return this.mouseDown;
    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyTyped(KeyEvent e) {
        keyEventSystem.onKeyTyped(e);
    }

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyPressed(KeyEvent e) {
        this.keyState.add(e.getCode());
        keyEventSystem.onKeyPressed(e);
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyReleased(KeyEvent e) {
        this.keyState.remove(e.getCode());
        keyEventSystem.onKeyReleased(e);
    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseClicked(Vec2d e) {
        mouseEventSystem.onMouseClicked(e);
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMousePressed(Vec2d e) {
        this.mouseDown = true;
        this.mousePosition = e;
        mouseEventSystem.onMousePressed(e);
    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseReleased(Vec2d e) {
        this.mouseDown = false;
        this.mousePosition = e;
        mouseEventSystem.onMouseReleased(e);
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseDragged(Vec2d e) {
        this.mousePosition = e;
        mouseEventSystem.onMouseDragged(e);
    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseMoved(Vec2d e) {
        this.mousePosition = e;
        mouseEventSystem.onMouseMoved(e);
    }

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    public void onMouseWheelMoved(ScrollEvent e) {
        mouseEventSystem.onMouseWheelMoved(e);
    }



    public void saveToXMLFile(String path){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document

            Element rootElement = doc.createElement("GameWorld");

            for(GameObject g :this.gameObjects){
                rootElement.appendChild(g.getXML(doc));
            }

            doc.appendChild(rootElement);
            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File(path));
            transformer.transform(source, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallBack{
        void callBack(Component c);
    }

    public void loadFromXML(String path, CallBack callBack){
        this.clearAllGameObjects();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        List<Component> partiallyLoadedComponents = new ArrayList<Component>();

        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(path);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("GameObject");
            for(int i = 0; i < nList.getLength(); i++){
                Node n = nList.item(i);
                GameObject g = new GameObject(this,Integer.parseInt(n.getAttributes().getNamedItem("layer").getNodeValue()));
                this.addGameObject(g);

                NodeList childList = n.getChildNodes();
                for(int j = 0; j < childList.getLength(); j++){
                    Node component = childList.item(j);
                    if(component.getNodeType() != Node.ELEMENT_NODE) continue;
                    try {
                        Element element = (Element) component;
                        Component c = Component.getComponentFromXML(element);
                        if(c.NOT_FULLY_LOADED){
                            partiallyLoadedComponents.add(c);
                        }
                        if (element.getNodeName().endsWith("TransformComponent")) {
                            g.setTransform((TransformComponent) c);
                        } else {
                            g.addComponent(c);
                        }
                    }  catch (ClassNotFoundException e) {
                        System.err.println("Failed to find component for node:" + component.toString());
                        e.printStackTrace();
                    } catch(NoSuchMethodException e){
                        System.err.println("Component does not have loading implemented" + component.toString());
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        System.err.println(component.getNodeName());
                        e.printStackTrace();
                    }
                }

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        for(Component c : partiallyLoadedComponents){
            callBack.callBack(c);
            c.NOT_FULLY_LOADED = false;
        }


    }
}
