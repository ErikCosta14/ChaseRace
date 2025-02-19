package corridadecarrosvirtual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Pista extends JPanel implements ActionListener{
    private final Image fundo;
    private Player player;
    private Timer timer;
    private Computador maquina;
    private int contCol = 0;
    private int selCarro;
    private int numGerado = 30;
    private String telaFim;
    private boolean jogando;
    private Random gerarNum = new Random();
    private ArrayList<Colisao> colisoes = new ArrayList<>();
    private JButton btnReiniciar;
    
    public Pista(){
        setFocusable(true);
        setDoubleBuffered(true);
        
        ImageIcon referencia = new ImageIcon("src\\img\\PistaPadrao.png");
        fundo = referencia.getImage();
        
        try {
            AudioUtil.tocarSom("src\\audio\\MusicaFundo.wav");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(e -> {
            resetGame();
        });
        
        // Adicionar botão ao painel
        this.setLayout(new BorderLayout());
        this.add(btnReiniciar, BorderLayout.WEST);
        btnReiStyle();
        
        iniciarJogo();
    }
    
    public void iniciarJogo() {
        for(int i = 0; i < 2; i++) {
            selCarro = gerarNum.nextInt(0, 10);
            
            while(selCarro == numGerado){
                selCarro = gerarNum.nextInt(0, 10);
            }
            
            if(i == 0){
                player = new Player();
                player.load(player.carros[selCarro]);
                numGerado = selCarro;
            }else {
                maquina = new Computador();
                maquina.load(maquina.carros[selCarro]);
            }
        }
        contCol = 0;
        colisoes.clear();
        colisoesPista();
        
        addKeyListener(new TecladoAdapter());
        
        timer = new Timer(16, this);
        timer.start();
        jogando = true;
    }
    
    public void resetGame() {
        timer.stop();
        jogando = false;
        contCol = 0;
        
        colisoes.clear();
        
        iniciarJogo();
        requestFocusInWindow();
    }
    
    private void btnReiStyle(){
        this.btnReiniciar.setBackground(Color.red);
        this.btnReiniciar.setFocusPainted(false);
        this.btnReiniciar.setForeground(Color.white);
    }
    
    private void colisoesPista(){
        int numColisoes = gerarNum.nextInt(50,70);
        for(int i = 0; i < numColisoes; i++){
            int pos = gerarNum.nextInt(400,900);
            int vel = gerarNum.nextInt(30, 35);

            Colisao col = new Colisao(pos,vel);
            int nme = gerarNum.nextInt(1, 4);
            
            if(nme == 1){
                col.load("Barreira");
            }
            if(nme == 2){
                col.load("Pneu");
            }
            if(nme == 3){
                col.load("PlacaPare");
            }
            colisoes.add(col);
        }
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D grafico = (Graphics2D) g;
        
        if(jogando == true){
            grafico.drawImage(fundo, -270, 0, null);
            grafico.drawImage(player.getImagem(), player.getX(), player.getY(), this);
            grafico.drawImage(maquina.getImagem(), maquina.getX(), maquina.getY(), this);
        
            for(int i = 0; i < colisoes.size(); i++){
                Colisao cl = colisoes.get(i);
                grafico.drawImage(cl.getImagem(), cl.getX(), cl.getY(), this);
            }
        }else {
            ImageIcon fimJogo = new ImageIcon("src\\img\\" + telaFim + ".png");
            grafico.drawImage(fimJogo.getImage(), -270, 0, null);
        }
         
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        maquina.update(player.getDx(), player.getDy()); 
        
        visibleCol();
        checarColisoes();
        
        if(maquina.getY() > 850 || player.getX() < 390 || player.getX() > 950){
            telaFim = "Perdeu";
            jogando = false;
            timer.stop();
            JOptionPane.showMessageDialog(this, "Saiu da pista!\nPara reiniciar o jogo, clique no botão a esquerda!");
        }
        
        repaint();
    }
    
    private void visibleCol(){
        for(int i = 0; i < colisoes.size(); i++){
            Colisao cl = colisoes.get(i);
            ScheduledExecutorService tempo = Executors.newScheduledThreadPool(1);
            tempo.schedule(() -> cl.update(), 2+i, TimeUnit.SECONDS);
            
            if (cl.getY() > cl.alturaTela){
                colisoes.remove(i);
                contCol++;
            }
        }
        
        if(contCol > colisoes.size()){
            telaFim = "Ganhou";
            jogando = false;
            timer.stop();
            JOptionPane.showMessageDialog(this, "Você desviou de todos os obstáculos!\nPara reiniciar o jogo, clique no botão a esquerda!");
        }
    }
    
    public void checarColisoes(){
        Rectangle formaCar = player.getBounds();
        Rectangle formaCom = maquina.getBounds();
        Rectangle formaCol;
        
        for(int i = 0; i < colisoes.size(); i++){
            Colisao colTemp = colisoes.get(i);
            formaCol = colTemp.getBounds();
            
            if(formaCar.intersects(formaCol) || formaCom.intersects(formaCol)){
                jogando = false;
                if(formaCar.intersects(formaCol)){
                    telaFim = "Perdeu";
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Você bateu em um obstáculo!\nPara reiniciar o jogo, clique no botão a esquerda!");
                }else {
                    telaFim = "Ganhou";
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Seu adversário bateu em um obstáculo!\nPara reiniciar o jogo, clique no botão a esquerda!");
                }
            }
        }
    }
    
    private class TecladoAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            player.keyPressed(e);
        }
        
        @Override
        public void keyReleased(KeyEvent e){
            player.keyRelease(e);
        }
    }
}
