/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author carlos.boch
 */
public class Window extends JFrame {

    private Screen screen;
    private boolean running = true;

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Window() {
        setSize(1200, 600);
        setExtendedState(MAXIMIZED_BOTH);
        
        setLocationRelativeTo(null);
        setTitle("IABasics");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIgnoreRepaint(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                createBufferStrategy(2);
                final long ms = 17l;
                service.scheduleWithFixedDelay(new LogicUpdate(), ms, ms, TimeUnit.MILLISECONDS);
            }
            

            @Override
            public void windowClosed(WindowEvent e) {
                running = false;
                service.shutdownNow();
                Log.close();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                running = false;
                service.shutdownNow();
            }

        });
        
        

        addKeyListener(new KeyAdapter() {
            private void setKeysState(KeyEvent e, boolean state) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        Input.LEFT = state;
                        break;
                    case KeyEvent.VK_RIGHT:
                        Input.RIGHT = state;
                        break;
                    case KeyEvent.VK_UP:
                        Input.UP = state;
                        break;
                    case KeyEvent.VK_DOWN:
                        Input.DOWN = state;
                        break;
                    case KeyEvent.VK_SPACE:
                        Input.ACTION1 = state;
                        break;
                    case KeyEvent.VK_ALT:
                        Input.ACTION2 = state;
                        break;
                    case KeyEvent.VK_CONTROL:
                        Input.ACTION3 = state;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent evt) {
                setKeysState(evt, true);
            }

            @Override
            public void keyReleased(KeyEvent evt) {
                setKeysState(evt, false);
            }
        });

    }

    public void paintScreen() {
        BufferStrategy strategy = getBufferStrategy();

        if (strategy == null) {
            return;
        }

        Graphics g = strategy.getDrawGraphics();

        if (g == null) {
            return;
        }

        // Criamos um contexto gráfico que não leva em conta as bordas
        Graphics g2 = g.create(getInsets().left, getInsets().top, getWidth()
                - getInsets().right, getHeight() - getInsets().bottom);
        draw((Graphics2D) g2);

        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
        g2.dispose();
    }

    public void draw(Graphics2D g2d) {
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        screen.draw(g2d);
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    private class LogicUpdate implements Runnable {

        @Override
        public void run() {
            long before = System.currentTimeMillis() - 1;
            if(running) {

                long time = before - System.currentTimeMillis();
                before = System.currentTimeMillis();

                Physics.deltaTime = time / 1000.0f;

                if (running) {
                    World.getInstance().update();
                    paintScreen();
                }
            }
        }
    }

    private static class ThreadFactory implements java.util.concurrent.ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "GAME-THREAD");

            t.setUncaughtExceptionHandler((Thread t1, Throwable e) -> {
                e.printStackTrace(System.out);
            });

            return t;

        }

    }
}
