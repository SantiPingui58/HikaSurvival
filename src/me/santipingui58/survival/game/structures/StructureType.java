package me.santipingui58.survival.game.structures;

import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.gui.structures.BancoMenu;
import me.santipingui58.survival.gui.structures.HospitalMenu;
import me.santipingui58.survival.gui.structures.MagoMenu;
import me.santipingui58.survival.gui.structures.MensajeroMenu;
import me.santipingui58.survival.gui.structures.SeguridadMenu;
import me.santipingui58.survival.gui.structures.TrainMenu;

public enum StructureType {

	BANK,
	SECURITY,
	PETS,
	MAIL,
	KING,
	MAGE,
	HOSPITAL,
	TRAIN;

	
	
	
	
	public String getName() {
		switch (this) {
		case BANK:
			return "§a§lBanco";
		case KING:
			return "§6§lCastillo de Nobles";
		case MAGE:
			return "§5§lCasa de Mago";
		case MAIL:
			return "§e§lCasa de Mensajeria";
		case PETS:
			return "§a§lTienda de Mascotas";
		case SECURITY:
			return "§f§lCasa de Seguridad";
		case HOSPITAL:
			return "§c§lHopistal";
		case TRAIN:
			return "§3§lBOLETERIA";
		default: return null;
		}
	}
	
	
	public GUIBuilder getGUI(HikaSurvival hikaSurvival,Player p) {
		switch (this) {
		case BANK:
			return new BancoMenu(hikaSurvival,p);
		case KING:
			return null;
		case MAGE:
			return new MagoMenu(hikaSurvival,p);
		case MAIL:
			return new MensajeroMenu(p,hikaSurvival);
		case PETS:
			return null;
		case SECURITY:
			return new SeguridadMenu(hikaSurvival);
		case HOSPITAL:
			return new HospitalMenu(hikaSurvival, p);	
		case TRAIN:
			return new TrainMenu(hikaSurvival);
		default:
			return null;
		}
	}
	
	
	public String[] getWelcomeMessages() {
	
		switch (this) {
		case BANK:
			String[] bank = {"§7Bienvenido al banco","§7Aqui podras proteger tus §6SurviCoins §7y no perderlas al morir.", "§7Tambien ganaras un pequeño interes "
					+ "por guardar tus §6Survicoins§7.", "§7Si esto te interesa, deberás pagar el precio para abrir tu cuenta en el " + getName()};
			return bank;
		case KING:
			String[] king = {"§7Bienvenido! Este es el castillo de los Nobles","§7Desde aqui regulamos y controlamos los distintos §6Reinos §7de Hikaland", 
					"§7Si quieres §6crear un reino o unirte a uno§7, deberás pagar el precio para asociarte al " + getName()};
			return king;
		case MAGE:
			String[] mage = {"§7Bienvenido... Soy el mago de este pueblo."," §7Durante años he aprendido de magia para poder §d§nteletransportarme a donde yo quiera§7.",
					"§7Si también quieres aprender estos trucos, deberás pagar el acceso a " + getName()};
			return mage;
		case MAIL:
			String[] mail = {"§7Hola! Soy el mensajero de este pueblo!", "Yo me encargo de §e§nenviar todas las cartes y mensajes a todos los reinos§7, por lo que siempre estoy muy ocupado",
					"Si también quieres enviar mensajes globales o privados, deberás pagar el acceso a " + getName()};
	return mail;
		case PETS:
			String[] pets = {"§7Bienvenido a la tienda de mascotas!", "§7Aqui puedes §badoptar una mascota §7para que sea tu fiel compañero. Quien además al crecer puede adquirir §cdiferentes habilidades§7!",
					"§7Si estas interesado en obtener una mascota, deberás pagar el acceso a " + getName()};
			return pets;
		case SECURITY:
			String[] security = {"§7Hola. Soy el encargado de la seguridad de este pueblo.","§7Todas las personas que quieren §f§oproteger sus casas y pertenencias §facuden a mi.",
					"§7Si también quieres tener seguridad en tu casa, deberás pagar el acceso a " + getName()};
			return security;
		case HOSPITAL:
			String[] hospital = {"§7Bienvenido al hospital.","§7Aqui tratamos pacientes con distintas enfermedades, y damos algunos servicios para la salud.",
					"§7Si quieres gosar de estos beneficios, deberás hacer una donación al" + getName()};
			return hospital;
		default: 
			return null;
		
		}
	}


	public int getPrice() {
		switch (this) {
		case BANK:
			return 100;
		case KING:
			return 500;
		case MAGE:
			return 500;
		case MAIL:
			return 100;
		case PETS:
			return 1000;
		case SECURITY:
			return 500;
		case HOSPITAL:
			return 2000;
		default:
			return 0;
		
		}
	}
	

	
}
