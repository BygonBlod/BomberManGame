package model.IAutils;

import model.Agent.Agent;
import model.BomberManGame;
import model.utils.InfoBomb;
import model.utils.InfoItem;

import java.util.ArrayList;

public class Research {

    /**
     * retourne l'ennemi le plus proche
     * @param a
     * @param game
     * @return
     */
    public static Position searchEnnemi(Agent a, BomberManGame game) {
        Position res=null;
        double distRes=0;
        for(Agent ennemi:game.getListEnnemi()) {
            if(res==null){
                distRes=Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
                res=new Position(200,200);
            }
            else {
                double distEnnemi=Math.sqrt(Math.pow((a.getX()-ennemi.getX()),2)+Math.pow((a.getY()-ennemi.getY()),2));
                distRes=Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
                if(distEnnemi!=0)
                	if(distEnnemi<distRes)res=new Position(ennemi.getX(),ennemi.getY());
            }
        }
        return res;
    }

    /**
     * retourne l'agent le plus proche
     * @param a
     * @param game
     * @return
     */
    public static Position searchEnnemi2(Agent a,BomberManGame game){
        Position res=null;
        for(Agent ennemi:game.getListEnnemi()) {
            if(res==null)res=new Position(200,200);
            else {
                double distEnnemi=Math.sqrt(Math.pow((a.getX()-ennemi.getX()),2)+Math.pow((a.getY()-ennemi.getY()),2));
                double distRes=Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
                if(distEnnemi!=0)
                	if(distEnnemi<distRes) {
                		res.setX(ennemi.getX());
                		res.setY(ennemi.getY());
                	}
            }
        }
        for(Agent bomberman:game.getListBomberMan()) {
            if(res==null)res=new Position(200,200);
            else {
                double distEnnemi=Math.sqrt(Math.pow((a.getX()-bomberman.getX()),2)+Math.pow((a.getY()-bomberman.getY()),2));
                double distRes=Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
                if(distEnnemi!=0)
                	if(distEnnemi<distRes) {
                		res.setX(bomberman.getX());
                		res.setY(bomberman.getY());
                	}
            }
        }
        return res;
    }

    /**
     * retourne la liste des positions des bombs les plus proches
     * @param a
     * @param game
     * @return
     */
    public static ArrayList<Position> getBomb(Agent a, BomberManGame game){
        ArrayList<Position> res=new ArrayList<>();
        for(InfoBomb bomb:game.getListBomb()){
            if(a.getX()<bomb.getX()+5 && a.getX()>bomb.getX()-5 && a.getY()<bomb.getY()+5 && a.getY()>bomb.getY()-5) {
                res.add(new Position(bomb.getX(),bomb.getY()));
            }
        }
        System.out.println(" Bombs:"+res);
        return res;
    }

    /**
     * retourne la liste des ennemis les plus proches
     * @param a
     * @param game
     * @return
     */
    public static ArrayList<Position> getEnnemis(Agent a,BomberManGame game){
        ArrayList<Position> res=new ArrayList<>();
        for(Agent agent:game.getListEnnemi()){
            if(a.getX()<agent.getX()+5 && a.getX()>agent.getX()-5 && a.getY()<agent.getY()+5 && a.getY()>agent.getY()-5) {
                res.add(new Position(agent.getX(),agent.getY()));
            }
        }
        System.out.println(" Ennemis:"+res);
        return res;
    }
    
    /**
     * retourne l'item positif le plus proche
     * @param a
     * @param game
     * @return
     */
    public static Position searchGoodItem(Agent a, BomberManGame game){
        Position res=null;
        int distRes=0;
        for(InfoItem item:game.getListItem()){
            if(res==null){
            	res=new Position(item.getX(),item.getY());
                distRes= (int) Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
                
            }
            else {
                double distEnnemi=Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
                distRes= (int) Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
                if(distEnnemi<distRes)res=new Position(res.getX(),res.getY());
            }
        }
        System.out.println(" Good item:"+res);
        return res;
    }
}
