package utilities;

public enum FigureType {

    Dragon("dragon", "images/dragon.png", 0),
    Jester("jester", "images/jester.png", 1),
    King("king", "images/king.png", 2),
    Knight("knight", "images/knight.png", 3),
    Scout("scout","images/scout.png", 4),
    SpearHorseman("spear horseman", "images/horseman-spear.png", 5),
    Spearman("spearman", "images/spearman.png", 6),
    Squire("squire", "images/squire.png", 7),
    Sword("sword","images/sword.png", 8), 
    SwordHorseman("sword horseman","images/horseman-sword.png", 9),
    Swordsman("swordsman","images/swordsman.png", 10), 
    Wizard("wizard","images/wizard.png", 11),
    Null("null", "images/null.png", 100);
	
	
    private String name;
    private String imagePath;
    private int index;

    FigureType(String name, String imagePath, int index) {
        this.name = name;
        this.imagePath = imagePath;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return imagePath;
    }
    
    public int getIndex(){
    	return index;
    }
}
