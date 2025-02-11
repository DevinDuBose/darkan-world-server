// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.utils.music;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonIOException;
import com.rs.lib.file.JsonFileManager;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.annotations.ServerStartupEvent;

@PluginEventHandler
public class Music {
	
	private static Map<Integer, Song> MUSICS = new HashMap<>();//Full music listing
	private static Map<Integer, int[]> MUSICS_REGION = new HashMap<>();//hints & unlocks

    private static Map<Integer, Genre> GENRE_REGION = new HashMap<>();//Genre per region, object is a string and int[] songIds pair

	@ServerStartupEvent
	public static void init() {
		try {
			Song[] songs = (Song[]) JsonFileManager.loadJsonFile(new File("./data/music.json"), Song[].class);
			for (Song s : songs) {
				MUSICS.put(s.getId(), s);
				for (int regionId : s.getRegionIds()) {
					if (MUSICS_REGION.get(regionId) == null)
						MUSICS_REGION.put(regionId, new int[] { s.getId() });
					else {
						int[] musicIds = MUSICS_REGION.get(regionId);
						int[] newMusicIds = new int[musicIds.length+1];
						for (int i = 0;i < musicIds.length;i++)
							newMusicIds[i] = musicIds[i];
						newMusicIds[musicIds.length] = s.getId();
						MUSICS_REGION.put(regionId, newMusicIds);
					}
				}
			}

            Genre[] genres = (Genre[]) JsonFileManager.loadJsonFile(new File("./data/musicGenre.json"), Genre[].class);
            for(Genre g : genres)
                for(int regionId : g.getRegionIds())
                    GENRE_REGION.put(regionId, g);//none of the values can be empty
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

	public static int[] getRegionMusics(int regionId) {
		return MUSICS_REGION.get(regionId);
	}

	public static Song getSong(int musicId) {
		return MUSICS.get(musicId);
	}

    public static Genre getGenre(int regionId) {
        if(GENRE_REGION.containsKey(regionId)) {
            Genre g = GENRE_REGION.get(regionId);
            if(g.getComment().equalsIgnoreCase("isdone"))
                return g;
            else
                return null;
        }
        else
            return null;
    }
}
