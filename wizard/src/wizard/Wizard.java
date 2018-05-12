
package wizard;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Wizard extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    
    public Wizard() {
        new Window(1000, 563, "Wizard Game", this);
        start();
        
        handler = new Handler(); 
        this.addKeyListener(new KeyInput(handler));
        
        handler.addObject(new Player(100,100, ID.Player, handler));
        
    }
    
    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountofTicks = 60.0;
        double ns = 1000000000 / amountofTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) /ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                //updates++;
                delta--;
            }
            render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //updates = 0;
            }
        }
        stop();
    }
    
    public void tick() {
        handler.tick();
    }
    
    
   public void render() {
       BufferStrategy bs = this.getBufferStrategy();
       if(bs == null){
           this.createBufferStrategy(3);
           return;
       }
       
       Graphics g = bs.getDrawGraphics();
       //////////////////////////////////////
       
       g.setColor(Color.red);
       g.fillRect(0, 0, 1000, 563);
        
       handler.render(g);
     
       
       //////////////////////////////////////
       g.dispose();
       bs.show();
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Wizard();
    }
    
}
