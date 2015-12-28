package alexmog.rulemastersworld.server.packetactions;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.maths.MathsHelper;
import alexmog.rulemastersworld.packets.PlayerUseSpellPacket;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.server.PacketAction;
import alexmog.rulemastersworld.skills.ActivableSkill;
import alexmog.rulemastersworld.skills.Skill;

public class PlayerUseSpellAction implements PacketAction {

	@Override
	public void run(AccountConnection connection, Object packet)
			throws Exception {
		PlayerUseSpellPacket p = (PlayerUseSpellPacket) packet;
		Skill s = connection.getPlayer().getSkills().get(p.spellid);
		if (s != null) {
			if (s instanceof ActivableSkill) {
				ActivableSkill skill = (ActivableSkill) s;
				if (!skill.isCooledDown()) {
					TchatMsgPacket msg = new TchatMsgPacket();
					msg.msg = "Spell not ready.";
					msg.username = "ERROR";
					connection.sendTCP(msg);
					return;
				}
				if (p.entityId < 0) {
					if (MathsHelper.getDistance(p.posX, p.posY, connection.getPlayer().getPosition().x, connection.getPlayer().getPosition().y)
					        <= (skill.getRange() * connection.getPlayer().getMapInstance().getMap().getTmxMap().getTileheight())) {
						skill.activate(p.posX, p.posY);
					} else {
						TchatMsgPacket msg = new TchatMsgPacket();
						msg.msg = "Not in range.";
						msg.username = "ERROR";
						connection.sendTCP(msg);
					}
				} else {
					Entity e = connection.getPlayer().getMapInstance().getGameInstance().getGameMode().getEntityManager().getEntityById(p.entityId);
					if (e != null && e instanceof LivingEntity) {
						if (MathsHelper.getDistance(e.getPosition(), connection.getPlayer().getPosition())
						        <= (skill.getRange() * connection.getPlayer().getMapInstance().getMap().getTmxMap().getTileheight())) {
							skill.activate((LivingEntity)e);
						} else {
							TchatMsgPacket msg = new TchatMsgPacket();
							msg.msg = "Entity not in range.";
							msg.username = "ERROR";
							connection.sendTCP(msg);
						}
					} else {
						TchatMsgPacket msg = new TchatMsgPacket();
						msg.msg = "Entity not found.";
						msg.username = "ERROR";
						connection.sendTCP(msg);
					}
				}
			} else {
				Log.info("Player " + connection.getPlayer().getName() + " (" + connection.getUsername()
						+ ") tried to use a non activable spell, it's bad :(");
				TchatMsgPacket msg = new TchatMsgPacket();
				msg.msg = "You can't use a passive spell.";
				msg.username = "ERROR";
				connection.sendTCP(msg);
			}
		} else {
			Log.info("Player " + connection.getPlayer().getName() + " (" + connection.getUsername()
					+ ") tried to use a non learned spell, it's bad :(");
			TchatMsgPacket msg = new TchatMsgPacket();
			msg.msg = "You haven't learned this spell :(";
			msg.username = "ERROR";
			connection.sendTCP(msg);
		}
	}

	@Override
	public boolean needLoggedIn() {
		return true;
	}

}
