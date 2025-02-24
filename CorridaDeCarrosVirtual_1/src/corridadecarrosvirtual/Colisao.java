package corridadecarrosvirtual;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Colisao implements ILoading{
    private Image imagem;
    public int alturaTela = 800;
    private int altura;
    private int largura;
    private int x;
    private int y;
    private int velocidade;
    
    public Colisao(int x, int v) {
        //iniciando a colisão nas posiçõe se velocidades definidas
        this.x = x;  
        this.y = -100;
        this.velocidade = v;
    }
    
    @Override
    public void load(String colisao) {
        //carrega que imagem é que vai ser a colisão
        ImageIcon referencia = new ImageIcon("src\\img\\" + colisao + ".png");
        imagem = referencia.getImage();
        this.altura = imagem.getHeight(null);
        this.largura = imagem.getWidth(null);
    }
    
    public void update(){
        //a colisão anda verticalmente
        y += velocidade;
    }
    
    public Rectangle getBounds(){
        //retorna os lados do retangulo da imagem, que é usada para o hit-box
        return new Rectangle(x,y,largura,altura);
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
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
