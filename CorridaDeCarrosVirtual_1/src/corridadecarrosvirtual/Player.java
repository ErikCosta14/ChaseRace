package corridadecarrosvirtual;

import java.awt.event.KeyEvent;

public class Player extends Competidor{
    public void keyPressed(KeyEvent tecla){
        //lê a tecla que foi pressionada e manda quanto é o valor da direção de x ou y
        int codigo = tecla.getKeyCode();
        
        if(codigo == KeyEvent.VK_W){
            dy = -15;
        }
        if(codigo == KeyEvent.VK_A){
            dx = -15;
        }
        if(codigo == KeyEvent.VK_S){
            dy = 10;
        }
        if(codigo == KeyEvent.VK_D){
            dx = 15;
        }
    }
    
    public void keyRelease(KeyEvent tecla){
        //lê que tecla foi solta para que o player não continue andando
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
