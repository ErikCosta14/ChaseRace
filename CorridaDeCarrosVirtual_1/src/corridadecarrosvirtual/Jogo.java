package corridadecarrosvirtual;

import javax.swing.JFrame;

public class Jogo extends JFrame{
    Pista pista = new Pista(); //criando uma nova pista para o jogo
    
    public Jogo() {
        //criando a tela que precisa ser criada
        add(pista);
        setTitle("Chase Race");
        setSize(1280,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args){
        new Jogo(); //chamando a função do jogo para iniciar a partida
    }
}
