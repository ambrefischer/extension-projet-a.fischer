import Controller.ControlCenter;
import View.View;

public class Main {
    public static void main(String[] args) {
        try {
            Model m = new Model("Sylvain", "Saurel");
            View v = new View("MVC with SSaurel");
            Controller c = new Controller(m, v);
            c.initController();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
