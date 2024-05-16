package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;
	MainMenu mainMenu;
	public boolean upPressed, downPressed, leftPressed, rightPressed, slashPressed, enterPressed, QPressed, EPressed, RPressed, TPressed, YPressed, UPressed, IPressed, OPressed, PPressed, FPressed, GPressed, HPressed, JPressed, KPressed, LPressed, ZPressed, XPressed, CPressed, VPressed, BPressed, NPressed, MPressed, spacePressed;

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
		if(code == KeyEvent.VK_SLASH) {
			slashPressed = true;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		//Putting this for chat feature:
		if(code == KeyEvent.VK_Q) {
			QPressed = true;
		}if(code == KeyEvent.VK_E) {
			EPressed = true;
		}if(code == KeyEvent.VK_R) {
			RPressed = true;
		}if(code == KeyEvent.VK_T) {
			TPressed = true;
		}if(code == KeyEvent.VK_Y) {
			YPressed = true;
		}if(code == KeyEvent.VK_U) {
			UPressed = true;
		}if(code == KeyEvent.VK_I) {
			IPressed = true;
		}if(code == KeyEvent.VK_O) {
			OPressed = true;
		}if(code == KeyEvent.VK_P) {
			PPressed = true;
		}if(code == KeyEvent.VK_F) {
			FPressed = true;
		}if(code == KeyEvent.VK_G) {
			GPressed = true;
		}if(code == KeyEvent.VK_H) {
			HPressed = true;
		}if(code == KeyEvent.VK_J) {
			JPressed = true;
		}if(code == KeyEvent.VK_K) {
			KPressed = true;
		}if(code == KeyEvent.VK_L) {
			LPressed = true;
		}if(code == KeyEvent.VK_Z) {
			ZPressed = true;
		}if(code == KeyEvent.VK_X) {
			XPressed = true;
		}if(code == KeyEvent.VK_C) {
			CPressed = true;
		}if(code == KeyEvent.VK_V) {
			VPressed = true;
		}if(code == KeyEvent.VK_B) {
			BPressed = true;
		}if(code == KeyEvent.VK_N) {
			NPressed = true;
		}if(code == KeyEvent.VK_M) {
			MPressed = true;
		}if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
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
		if(code == KeyEvent.VK_SLASH) {
			slashPressed = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if(code == KeyEvent.VK_Q) {
			QPressed = false;
		}if(code == KeyEvent.VK_E) {
			EPressed = false;
		}if(code == KeyEvent.VK_R) {
			RPressed = false;
		}if(code == KeyEvent.VK_T) {
			TPressed = false;
		}if(code == KeyEvent.VK_Y) {
			YPressed = false;
		}if(code == KeyEvent.VK_U) {
			UPressed = false;
		}if(code == KeyEvent.VK_I) {
			IPressed = false;
		}if(code == KeyEvent.VK_O) {
			OPressed = false;
		}if(code == KeyEvent.VK_P) {
			PPressed = false;
		}if(code == KeyEvent.VK_F) {
			FPressed = false;
		}if(code == KeyEvent.VK_G) {
			GPressed = false;
		}if(code == KeyEvent.VK_H) {
			HPressed = false;
		}if(code == KeyEvent.VK_J) {
			JPressed = false;
		}if(code == KeyEvent.VK_K) {
			KPressed = false;
		}if(code == KeyEvent.VK_L) {
			LPressed = false;
		}if(code == KeyEvent.VK_Z) {
			ZPressed = false;
		}if(code == KeyEvent.VK_X) {
			XPressed = false;
		}if(code == KeyEvent.VK_C) {
			CPressed = false;
		}if(code == KeyEvent.VK_V) {
			VPressed = false;
		}if(code == KeyEvent.VK_B) {
			BPressed = false;
		}if(code == KeyEvent.VK_N) {
			NPressed = false;
		}if(code == KeyEvent.VK_M) {
			MPressed = false;
		}if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

}
