package corridadecarrosvirtual;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

abstract class Competidor implements IComp, ILoading{
    protected Image imagem;
    protected int altura;
    protected int largura;
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected String[] carros = {"Alpine", "AstonMartin", "Ferrari", "Haas", "McLaren", "Mercedes", "RedBull", "Sauber", "ToroRosso", "Williams"}; //vetor com todos os carros que podem aparecer
    
    //construtor do competidor que diz a posição inical em jogo
    protected Competidor(){
        this.x = 640;
        this.y = 400;
    }
    
    @Override
    public void update(){
        //movimenta o competidor
        x += dx;
        y += dy;
    }
    
    @Override
    public void update(int px, int py){
        //movimenta o competidor porém sobrecarregado
        x += px;
        y += py;
    }
    
    @Override
    public void load(String carro){
        //carrega o gráfico do carro
        ImageIcon referencia = new ImageIcon("src\\img\\" + carro + ".png");
        imagem = referencia.getImage();
        this.altura = imagem.getHeight(null);
        this.largura = imagem.getWidth(null);
    }
    
    protected Rectangle getBounds(){
        //retorna os lados do retangulo da imagem, que é usada para o hit-box
        return new Rectangle(x,y,largura,altura);
    }
    
    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }

    protected Image getImagem() {
        return imagem;
    }
}
