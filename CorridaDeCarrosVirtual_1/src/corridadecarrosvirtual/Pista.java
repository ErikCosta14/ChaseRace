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
    private ArrayList<Colisao> colisoes = new ArrayList<>(); //lista de colisões
    private JButton btnReiniciar;
    
    //classe construtura da pista
    public Pista(){
        setFocusable(true); //deixando a janela do jogo como foco
        setDoubleBuffered(true);
        
        //adicionando a imagem de fundo na tela
        ImageIcon referencia = new ImageIcon("src\\img\\PistaPadrao.png");
        fundo = referencia.getImage();
        
        //adicionando a música de fundo
        try {
            AudioUtil.tocarSom("src\\audio\\MusicaFundo.wav");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
        //botão de reiniciar sendo adicionado
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(e -> {
            resetGame();
        });
        
        this.setLayout(new BorderLayout());
        this.add(btnReiniciar, BorderLayout.WEST);
        btnReiStyle(); //função para a configuração do botão
        
        iniciarJogo(); //iniciando o jogo
    }
    
    private void iniciarJogo() {
        //selecionando os carros aleatóriamente
        for(int i = 0; i < 2; i++) {
            selCarro = gerarNum.nextInt(0, 10);
            
            while(selCarro == numGerado){
                selCarro = gerarNum.nextInt(0, 10); //caso os carros sejam iguais, será trocado até o carro ser diferente
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
        colisoesPista(); //chamando a função para gerar colisões na pista
        
        addKeyListener(new TecladoAdapter()); //adicionando função para ler as teclas
        
        //iniciando o timer e o jogo oficialmente
        timer = new Timer(16, this); //deixa o jogo a 60 frames
        timer.start();
        jogando = true;
    }
    
    private void resetGame() {
        //resetando o game para as funções originais
        timer.stop();
        jogando = false;
        contCol = 0;
        
        colisoes.clear(); //limpando o array das colisões
        
        //reiniciando o game
        iniciarJogo();
        requestFocusInWindow();
    }
    
    // função para deixar o botão de reiniciar personalizado
    private void btnReiStyle(){
        this.btnReiniciar.setBackground(Color.red);
        this.btnReiniciar.setFocusPainted(false);
        this.btnReiniciar.setForeground(Color.white);
    }
    
    private void colisoesPista(){
        //gerando as colisões do jogo
        int numColisoes = gerarNum.nextInt(50,70); //gera aleatoriamente de 70 a 50 colisões
        
        for(int i = 0; i < numColisoes; i++){
            int pos = gerarNum.nextInt(400,900); //coloca as colisões nas posições aleatórias em x
            int vel = gerarNum.nextInt(30, 35); // gera as colisões de velocidades em 30 a 35
            
            //gera cada colisão
            Colisao col = new Colisao(pos,vel);
            int nme = gerarNum.nextInt(1, 4);
            
            //cada colisão pode ser uma de 3 opções
            if(nme == 1){
                col.load("Barreira");
            }
            if(nme == 2){
                col.load("Pneu");
            }
            if(nme == 3){
                col.load("PlacaPare");
            }
            colisoes.add(col); //adiciona as colisões em no array de colis~ies
        }
    }
    
    @Override
    public void paint(Graphics g){
        //gerando gráficos 2d
        Graphics2D grafico = (Graphics2D) g;
        
        if(jogando == true){
            //enquanto tiver jogando gera os gráficos dos players e colisões
            grafico.drawImage(fundo, -270, 0, null);
            grafico.drawImage(player.getImagem(), player.getX(), player.getY(), this);
            grafico.drawImage(maquina.getImagem(), maquina.getX(), maquina.getY(), this);
        
            for(int i = 0; i < colisoes.size(); i++){
                Colisao cl = colisoes.get(i);
                grafico.drawImage(cl.getImagem(), cl.getX(), cl.getY(), this);
            }
        }else {
            //caso o jogo acabe, aparece a tela de fim
            ImageIcon fimJogo = new ImageIcon("src\\img\\" + telaFim + ".png");
            grafico.drawImage(fimJogo.getImage(), -270, 0, null);
        }
         
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //movimentando os itens que tem que movimentar na tela
        player.update();
        maquina.update(player.getDx(), player.getDy()); 
        
        visibleCol(); //movimenta as colisões
        checarColisoes(); //checa se o player ou o computador bateu nas colisões
        
        //caso o player ou a maquina saia da pista o jogo acaba
        if(maquina.getY() > 750 || player.getX() < 390 || player.getX() > 950){
            telaFim = "Perdeu";
            jogando = false;
            timer.stop();
            JOptionPane.showMessageDialog(this, "Saiu da pista!\nPara reiniciar o jogo, clique no botão a esquerda!");
        }
        
        repaint(); //atualiza a tela
    }
    
    private void visibleCol(){
        //iniciando um contador que movimenta as colisões
        ScheduledExecutorService tempo = Executors.newScheduledThreadPool(1);
        for(int i = 0; i < colisoes.size(); i++){
            Colisao cl = colisoes.get(i);
            tempo.schedule(() -> cl.update(), 2+i, TimeUnit.SECONDS); // a cada 2+i segundos a colisão anda
            
            //depois da altura definida da tela a colisão é removida do array
            if (cl.getY() > cl.alturaTela){
                colisoes.remove(i);
                contCol++;
            }
        }
        
        //caso o player passe todos os obstaculos, ele vence a partida e o jogo para
        if(contCol > colisoes.size()){
            telaFim = "Ganhou";
            jogando = false;
            timer.stop();
            JOptionPane.showMessageDialog(this, "Você desviou de todos os obstáculos!\nPara reiniciar o jogo, clique no botão a esquerda!");
        }
    }
    
    public void checarColisoes(){
        //criando uma hit-box para os itens
        Rectangle formaCar = player.getBounds();
        Rectangle formaCom = maquina.getBounds();
        Rectangle formaCol;
        
        for(int i = 0; i < colisoes.size(); i++){
            Colisao colTemp = colisoes.get(i);
            formaCol = colTemp.getBounds();
            
            //caso as hit-box se encontrem, o jogo termina, pois bateram
            if(formaCar.intersects(formaCol) || formaCom.intersects(formaCol)){
                jogando = false;
                if(formaCar.intersects(formaCol)){ //caso seja o player, perde
                    telaFim = "Perdeu";
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Você bateu em um obstáculo!\nPara reiniciar o jogo, clique no botão a esquerda!");
                }else { //caso seja o computador, ganha
                    telaFim = "Ganhou";
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Seu adversário bateu em um obstáculo!\nPara reiniciar o jogo, clique no botão a esquerda!");
                }
            }
        }
    }
    
    private class TecladoAdapter extends KeyAdapter{
        //lendo o botão que pressiona e o botão que solta
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
