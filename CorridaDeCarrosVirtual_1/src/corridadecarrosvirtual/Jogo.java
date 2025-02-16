package corridadecarrosvirtual;

import javax.swing.JFrame;

public class Jogo extends JFrame{
    Pista pista = new Pista();
    public Jogo() {
        add(pista);
        setTitle("F1");
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args){
        new Jogo();
    }
}
