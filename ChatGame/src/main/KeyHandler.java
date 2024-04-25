package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;
	MainMenu mainMenu;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

	//calls upon the GamePanel class
	
	public KeyHandler(GamePanel gp, MainMenu mainMenu) {
		this.gp = gp;
		this.mainMenu= mainMenu;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		int code = e.getKeyCode();
		//title State
		if(gp.gameState == gp.titleState) {
			if(code == KeyEvent.VK_W) {
				gp.mainMenu.commandNum--;
				if(gp.mainMenu.commandNum <0) {
					gp.mainMenu.commandNum =1;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.mainMenu.commandNum++;
				if(gp.mainMenu.commandNum >1) {
					gp.mainMenu.commandNum=0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.mainMenu.commandNum == 0) {
					gp.gameState = gp.playState;

				}
				if(gp.mainMenu.commandNum == 1) {
					System.exit(0);
				}
			}
		}
		
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed= true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed= false;
}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
	}

}
