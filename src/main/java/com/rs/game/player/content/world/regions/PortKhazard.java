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
package com.rs.game.player.content.world.regions;

import com.rs.game.player.content.dialogue.Conversation;
import com.rs.game.player.content.dialogue.HeadE;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.NPCClickEvent;
import com.rs.plugin.handlers.NPCClickHandler;
import com.rs.utils.shop.ShopsHandler;

@PluginEventHandler
public class PortKhazard {
	public static NPCClickHandler handleKhazardShopkeeper = new NPCClickHandler(555) {
		@Override
		public void handle(NPCClickEvent e) {
			int option = e.getOpNum();
			if (option == 1)
				e.getPlayer().startConversation(new Conversation(e.getPlayer()) {{
					addNPC(e.getNPCId(), HeadE.HAPPY_TALKING, "Can I help you at all?");
					addNext(() -> {
                        ShopsHandler.openShop(e.getPlayer(), "khazard_general_store");
                    });
                    create();
				}});
			if (option == 3)
				ShopsHandler.openShop(e.getPlayer(), "khazard_general_store");

		}};
}
