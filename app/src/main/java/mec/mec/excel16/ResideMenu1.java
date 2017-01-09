package mec.mec.excel16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by gopikm on 21/7/16.
 */
public class ResideMenu1 implements View.OnClickListener {

   Activity activity;
    ResideMenu resideMenu;

    Context context;


    private ResideMenuItem Schedule;
    private ResideMenuItem map;
    private ResideMenuItem newsfeed;
    private ResideMenuItem gallery;
    private ResideMenuItem about;
    private ResideMenuItem competition;
    private ResideMenuItem talks;
    private ResideMenuItem workshops;
    private ResideMenuItem Innovations;
    private ResideMenuItem funzone;
    private ResideMenuItem proshow;
    private ResideMenuItem exihibition;


    public ResideMenu1(Activity activity,Context context){
        this.activity=activity;
        this.context=context;
}
    public ResideMenu main(){
        resideMenu = new ResideMenu(context);

        resideMenu.setBackground(R.drawable.residemenu);
        resideMenu.attachToActivity(activity);
        resideMenu.setMenuListener(menuListener);
        // create menu items;
        // resideMenu.setScaleValue(0.2f);

        Schedule = new ResideMenuItem(context, R.drawable.schedule, "Schedule");
        map = new ResideMenuItem(context, R.drawable.map, "Map");
        newsfeed = new ResideMenuItem(context, R.drawable.newspaper, "News");
        gallery = new ResideMenuItem(context, R.drawable.gallery, "Gallery");
        about = new ResideMenuItem(context, R.drawable.info, "About");
        competition = new ResideMenuItem(context, R.drawable.events, "Events");
        talks = new ResideMenuItem(context, R.drawable.talks, "Talks");
        workshops = new ResideMenuItem(context, R.drawable.ranks, "Rankings");
        Innovations = new ResideMenuItem(context, R.drawable.initiatives, "Social\nActivities");
        funzone = new ResideMenuItem(context, R.drawable.map, "Game\nZone");
        proshow = new ResideMenuItem(context, R.drawable.proshow, "Proshow");
        exihibition = new ResideMenuItem(context, R.drawable.flask, "Labs");

        // account.setBackgroundColor(getResources().getColor(R.color.black_semi_transparent));



        Schedule.setOnClickListener(this);
        map.setOnClickListener(this);
        newsfeed.setOnClickListener(this);
        gallery.setOnClickListener(this);
        about.setOnClickListener(this);
        competition.setOnClickListener(this);
        talks.setOnClickListener(this);
        workshops.setOnClickListener(this);
        Innovations.setOnClickListener(this);
        funzone.setOnClickListener(this);
        proshow.setOnClickListener(this);
        exihibition.setOnClickListener(this);

        resideMenu.addMenuItem(competition, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(talks, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(Innovations, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(exihibition, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(funzone, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(Schedule, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(workshops, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(newsfeed, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(gallery, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(proshow, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(map, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(about, ResideMenu.DIRECTION_RIGHT);
      //  resideMenu.addMenuItem(competition, ResideMenu.DIRECTION_RIGHT);







        return resideMenu;
    }
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void closeMenu() {
            //  Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(View view) {
         if (view == Schedule){
             Intent intent= new Intent(context,Shedule.class);
             context.startActivity(intent);

        }else if (view == map){
             Intent intent= new Intent(context,Map.class);
             context.startActivity(intent);

        }else if (view == newsfeed){
             Intent intent= new Intent(context,News.class);
             context.startActivity(intent);

        }
        else if (view == gallery){
             Intent intent= new Intent(context,gallery.class);
             context.startActivity(intent);

        }
        else if (view == about){
             Intent intent= new Intent(context,about.class);
             context.startActivity(intent);

        }
        else if (view == competition){
             Intent intent= new Intent(context,Events.class);
             context.startActivity(intent);

        }
        else if (view == talks){
             Intent intent= new Intent(context,Talks.class);
             context.startActivity(intent);
        }
        else if (view == Innovations){
             Intent intent= new Intent(context,Initiatives.class);
             context.startActivity(intent);

        }
        else if (view == funzone){
             Intent intent= new Intent(context,FunZone.class);
             context.startActivity(intent);

        }
        else if (view == proshow){
             Intent intent= new Intent(context,proshow.class);
             context.startActivity(intent);

        }
        else if(view == workshops){
             Intent intent= new Intent(context,LeaderBoard.class);
             context.startActivity(intent);

        }
         else if(view == exihibition){
             Intent intent= new Intent(context,exhibition.class);
             context.startActivity(intent);

         }
        resideMenu.closeMenu();

    }



}
