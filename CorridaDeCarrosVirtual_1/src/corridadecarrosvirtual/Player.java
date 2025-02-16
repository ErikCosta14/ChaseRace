package corridadecarrosvirtual;

import java.awt.event.KeyEvent;

public class Player extends Competidor{
    public void keyPressed(KeyEvent tecla){
        int codigo = tecla.getKeyCode();
        
        if(codigo == KeyEvent.VK_W){
            dy = -20;
        }
        if(codigo == KeyEvent.VK_A){
            dx = -20;
        }
        if(codigo == KeyEvent.VK_S){
            dy = 10;
        }
        if(codigo == KeyEvent.VK_D){
            dx = 20;
        }
    }
    
    public void keyRelease(KeyEvent tecla){
        int codigo = tecla.getKeyCode();
        
        if(codigo == KeyEvent.VK_W){
            dy = 0;
        }
        if(codigo == KeyEvent.VK_A){
            dx = 0;
        }
        if(codigo == KeyEvent.VK_S){
            dy = 0;
        }
        if(codigo == KeyEvent.VK_D){
            dx = 0;
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
