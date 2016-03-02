package utilities;

public final class Figure {
	private Figure(){
		
	}
	
	public static int resolveFight(FigureType currentFigure, FigureType figureAtFutureTile){ //return 0 if same, return 1 is current one is winning,
																	//return 2 if figure at next tile is winning, return 100 if attacker is FULL GAME WINNER 
		if(currentFigure == figureAtFutureTile && currentFigure != FigureType.Jester) {
    		return 0;
    	} else {
    		if(currentFigure == FigureType.Jester){
    			if(figureAtFutureTile == FigureType.Dragon){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.King){
    			if(figureAtFutureTile == FigureType.Dragon){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if (currentFigure == FigureType.Knight) {
    			if(figureAtFutureTile == FigureType.Dragon || figureAtFutureTile == FigureType.King){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.SpearHorseman){
    			if(figureAtFutureTile == FigureType.Dragon || figureAtFutureTile == FigureType.King || figureAtFutureTile == FigureType.Knight){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.SwordHorseman) {
    			if(figureAtFutureTile == FigureType.Dragon || figureAtFutureTile == FigureType.King || figureAtFutureTile == FigureType.Knight 
    					|| figureAtFutureTile == FigureType.SpearHorseman){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.Spearman) {
    			if(figureAtFutureTile == FigureType.Dragon || figureAtFutureTile == FigureType.King || figureAtFutureTile == FigureType.Knight 
    					|| figureAtFutureTile == FigureType.SpearHorseman || figureAtFutureTile == FigureType.SwordHorseman){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.Swordsman) {
    			if(figureAtFutureTile == FigureType.Dragon || figureAtFutureTile == FigureType.King || figureAtFutureTile == FigureType.Knight 
    					|| figureAtFutureTile == FigureType.SpearHorseman || figureAtFutureTile == FigureType.SwordHorseman || figureAtFutureTile == FigureType.Spearman){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.Squire) {
    			if(figureAtFutureTile == FigureType.Dragon || figureAtFutureTile == FigureType.King || figureAtFutureTile == FigureType.Knight 
    					|| figureAtFutureTile == FigureType.SpearHorseman || figureAtFutureTile == FigureType.SwordHorseman || figureAtFutureTile == FigureType.Spearman
    					|| figureAtFutureTile == FigureType.Swordsman){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.Wizard) {
    			if(figureAtFutureTile == FigureType.King || figureAtFutureTile == FigureType.Knight || figureAtFutureTile == FigureType.SpearHorseman 
    					|| figureAtFutureTile == FigureType.SwordHorseman || figureAtFutureTile == FigureType.Spearman || figureAtFutureTile == FigureType.Swordsman
    					|| figureAtFutureTile == FigureType.Squire){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else if(currentFigure == FigureType.Scout) {
    			if(figureAtFutureTile == FigureType.Dragon || figureAtFutureTile == FigureType.King || figureAtFutureTile == FigureType.Knight 
    					|| figureAtFutureTile == FigureType.SpearHorseman || figureAtFutureTile == FigureType.SwordHorseman || figureAtFutureTile == FigureType.Spearman
    					|| figureAtFutureTile == FigureType.Swordsman || figureAtFutureTile == FigureType.Wizard){
    				return 2;
    			} else if (figureAtFutureTile == FigureType.Sword){
    				return 100;
    			} else {
    				return 1;
    			}
    		} else {
    			System.out.println("Igrate s zmajem ili macem sto nije dobro");
    		}
    	}
    	return 0;
	}
	
}
