package utilities;

public enum FigureType {

    Dragon("dragon", "images/dragon.png"), Jester("jester", "images/jester.png"), King("king",
            "images/king.png"), Knight("knight", "images/knight.png"), Scout("scout",
                    "images/scout.png"), SpearHorseman("spear horseman", "images/horseman-spear.png"), Spearman(
                            "spearman", "images/spearman.png"), Squire("squire", "images/squire.png"), Sword("sword",
                                    "images/sword.png"), SwordHorseman("sword horseman",
                                            "images/horseman-sword.png"), Swordsman("swordsman",
                                                    "images/swordsman.png"), Wizard("wizard",
                                                            "images/wizard.png"), Null("null", "images/null.png");
    private String name;
    private String imagePath;

    FigureType(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return imagePath;
    }
}
