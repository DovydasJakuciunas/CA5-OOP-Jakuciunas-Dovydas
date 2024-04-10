package org.example.Extras;

import java.util.Comparator;
import org.example.DTOs.Game_Information;


public class GameNameComparator implements Comparator<Game_Information>
{

        @Override
        public int compare(Game_Information g1, Game_Information g2)
        {
            return g1.getGame_name().compareTo(g2.getGame_name());
        }

}
