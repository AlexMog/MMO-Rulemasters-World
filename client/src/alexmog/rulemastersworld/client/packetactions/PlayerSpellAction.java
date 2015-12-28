package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.PlayerSpellPacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.skills.Skill;

public class PlayerSpellAction implements PacketAction {

	public void run(Object packet, Client client) {
		PlayerSpellPacket p = (PlayerSpellPacket)packet;
		Main.game.getGameMode().getPlayerSkillsManager().addSkill(
				new Skill(p.spellId, Main.game.getGameMode(),
						p.name, p.description, p.cooldown, p.iconId, p.passive, p.targetType, p.range));
	}

}
