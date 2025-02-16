package corridadecarrosvirtual;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Colisao {
    private Image imagem;
    public int alturaTela = 800;
    private int altura;
    private int largura;
    private int x;
    private int y;
    private int velocidade;
    
    public Colisao(int x, int v) {
        this.x = x;  
        this.y = -100;
        this.velocidade = v;
    }
    
    public void load(String colisao) {
        ImageIcon referencia = new ImageIcon("src\\img\\" + colisao + ".png");
        imagem = referencia.getImage();
        this.altura = imagem.getHeight(null);
        this.largura = imagem.getWidth(null);
    }
    
    public void update(){
        y += velocidade;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,largura,altura);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public Image getImagem() {
        return imagem;
    }
}
