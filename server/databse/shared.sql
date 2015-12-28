-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2+deb7u2
-- http://www.phpmyadmin.net
--
-- Client: 5.196.154.204
-- Généré le: Lun 28 Décembre 2015 à 13:12
-- Version du serveur: 5.5.44
-- Version de PHP: 5.4.45-0+deb7u2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `eba_shared`
--

-- --------------------------------------------------------

--
-- Structure de la table `effects`
--

CREATE TABLE IF NOT EXISTS `effects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL,
  `template_id` int(11) NOT NULL,
  `duration` bigint(20) NOT NULL,
  `script_vars` text NOT NULL COMMENT 'in json format',
  `stackable` tinyint(1) NOT NULL,
  `icon_id` int(11) NOT NULL,
  `tic_time` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `infinite` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `template_id` (`template_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `effects`
--

INSERT INTO `effects` (`id`, `name`, `description`, `template_id`, `duration`, `script_vars`, `stackable`, `icon_id`, `tic_time`, `author_id`, `infinite`) VALUES
(1, 'DOTEffect', 'Test dot effect', 1, 10000, '[]', 1, 0, 5000, 0, 0),
(2, 'HealEffect', 'Test heal effect', 1, 10000, '[{"name":"dmgVar", "value": -5}]', 1, 1, 5000, 0, 1),
(3, 'DOTIntelEffect', 'DOT With intel usage', 2, 60000, '', 1, 0, 5000, 0, 0),
(5, 'TestAgain', 'test', 1, 120000, '[{"name":"dmgVar","value":"20","type":"int","description":"Used to set tic damages"}]', 0, 1, 5000, 2, 0);

-- --------------------------------------------------------

--
-- Structure de la table `effects_templates`
--

CREATE TABLE IF NOT EXISTS `effects_templates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL,
  `script_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `script_id` (`script_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `effects_templates`
--

INSERT INTO `effects_templates` (`id`, `name`, `description`, `script_id`, `author_id`) VALUES
(1, 'Basic DOT Effect', 'Basic DOT Effect', 7, 0),
(2, 'DOT With intel', 'DOT Using intelligence', 8, 0);

-- --------------------------------------------------------

--
-- Structure de la table `livingentities_templates`
--

CREATE TABLE IF NOT EXISTS `livingentities_templates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(250) NOT NULL,
  `script_id` int(11) NOT NULL,
  `strength` int(11) NOT NULL,
  `agility` int(11) NOT NULL,
  `intelligence` int(11) NOT NULL,
  `endurence` int(11) NOT NULL,
  `armor` int(11) NOT NULL,
  `speed` float NOT NULL,
  `script_vars` text NOT NULL COMMENT 'in json format',
  `skin_id` int(11) NOT NULL,
  `spells` varchar(255) NOT NULL,
  `items` varchar(255) NOT NULL,
  `use_astar` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `livingentities_templates`
--

INSERT INTO `livingentities_templates` (`id`, `name`, `description`, `script_id`, `strength`, `agility`, `intelligence`, `endurence`, `armor`, `speed`, `script_vars`, `skin_id`, `spells`, `items`, `use_astar`) VALUES
(1, 'Test', 'This is a test random entity', 2, 10, 1, 1, 10, 1, 1.5, '', 0, '', '', 1),
(2, 'Baloon', 'This is a baloon :o', 9, 10, 1, 1, 10000, 1000, 1.7, '', 0, '', '', 0);

-- --------------------------------------------------------

--
-- Structure de la table `projectiles`
--

CREATE TABLE IF NOT EXISTS `projectiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) NOT NULL,
  `script_id` int(11) NOT NULL,
  `script_vars` text NOT NULL COMMENT 'in json format',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `scripts`
--

CREATE TABLE IF NOT EXISTS `scripts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(250) NOT NULL,
  `script` text NOT NULL,
  `type` enum('Item','Skill','Effect','LivingEntity','GameObject','Projectile') NOT NULL,
  `author_id` int(11) NOT NULL,
  `istemplate` tinyint(1) NOT NULL,
  `script_vars` text NOT NULL COMMENT 'json format',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `scripts`
--

INSERT INTO `scripts` (`id`, `name`, `description`, `script`, `type`, `author_id`, `istemplate`, `script_vars`) VALUES
(1, 'Monster template', 'Monster template script', '// In every functions, the variable "entity" defines the entity who uses the script.\r\n\r\n/*\r\n	Called on the creation of the entity\r\n*/\r\nfunction init(entity) {}\r\n\r\n/*\r\n	Called when the entity get damages\r\n	\r\n	Parameters:\r\n		from - The entity from who comes the damages\r\n		amount - The amount of damages\r\n*/\r\nfunction onDamage(entity, from, amount) {}\r\n\r\n/*\r\n	Called when the entity get healed\r\n	\r\n	Parameters:\r\n			from - The entity from who comes the heals\r\n			amount - The amount of heals\r\n*/\r\nfunction onHeal(entity, from, amount) {}\r\n\r\n/*\r\n	Called when an entity enter the parent vision\r\n	\r\n	Parameters:\r\n		entity2 - The entity who is in the vision\r\n*/\r\nfunction onEntityInVision(entity, entity2) {}\r\n\r\n/*\r\n	Called on each loop\r\n	\r\n	Parameters:\r\n		delta - The loop delta value (time passed in ms between each loop)\r\n*/\r\nfunction onUpdate(entity, delta) {}\r\n\r\n/*\r\n	Called when an effect is added to the entity\r\n	\r\n	Parameters:\r\n		effect - The effect added\r\n*/\r\nfunction onEffectAdded(entity, effect) {}\r\n\r\n/*\r\n	Called when an effect is remove from the entity\r\n	\r\n	Parameters:\r\n		effect - The effect removed\r\n*/\r\nfunction onEffectRemoved(entity, effect) {}\r\n\r\n/*\r\n	Called when all effects are simultany cleared\r\n*/\r\nfunction onEffectsCleared(entity) {}\r\n\r\n/*\r\n	Called when an entity taunt our entity\r\n	\r\n	Parameters:\r\n		entity2 - The entity who taunted\r\n*/\r\nfunction onTaunt(entity, entity2) {}\r\n\r\n/*\r\n	Called when a collision occures\r\n	\r\n	Parameters:\r\n		entity2 - The collided entity\r\n*/\r\nfunction onCollision(entity, entity2) {}\r\n\r\n', 'LivingEntity', 1, 1, ''),
(2, 'Monster template', 'Monster template script', '// In every functions, the variable "entity" defines the entity who uses the script.\r\n\r\n/*\r\n	Called on the creation of the entity\r\n*/\r\nfunction init(entity) {}\r\n\r\n/*\r\n	Called when the entity get damages\r\n	\r\n	Parameters:\r\n		from - The entity from who comes the damages\r\n		amount - The amount of damages\r\n*/\r\nfunction onDamage(entity, from, amount) {}\r\n\r\n/*\r\n	Called when the entity get healed\r\n	\r\n	Parameters:\r\n			from - The entity from who comes the heals\r\n			amount - The amount of heals\r\n*/\r\nfunction onHeal(entity, from, amount) {}\r\n\r\n/*\r\n	Called when an entity enter the parent vision\r\n	\r\n	Parameters:\r\n		entity2 - The entity who is in the vision\r\n*/\r\nfunction onEntityInVision(entity, entity2) {}\r\n\r\n/*\r\n	Called on each loop\r\n	\r\n	Parameters:\r\n		delta - The loop delta value (time passed in ms between each loop)\r\n*/\r\nfunction onUpdate(entity, delta) {\r\nif (!entity.isMoving()) {\r\nvar x = Math.floor((Math.random() * 1000));\r\nvar y = Math.floor((Math.random() * 1000));\r\nentity.goTo(x, y);\r\n}\r\n}\r\n\r\n/*\r\n	Called when an effect is added to the entity\r\n	\r\n	Parameters:\r\n		effect - The effect added\r\n*/\r\nfunction onEffectAdded(entity, effect) {}\r\n\r\n/*\r\n	Called when an effect is remove from the entity\r\n	\r\n	Parameters:\r\n		effect - The effect removed\r\n*/\r\nfunction onEffectRemoved(entity, effect) {}\r\n\r\n/*\r\n	Called when all effects are simultany cleared\r\n*/\r\nfunction onEffectsCleared(entity) {}\r\n\r\n/*\r\n	Called when an entity taunt our entity\r\n	\r\n	Parameters:\r\n		entity2 - The entity who taunted\r\n*/\r\nfunction onTaunt(entity, entity2) {}\r\n\r\n/*\r\n	Called when a collision occures\r\n	\r\n	Parameters:\r\n		entity2 - The collided entity\r\n*/\r\nfunction onCollision(entity, entity2) {}\r\n\r\n', 'LivingEntity', 1, 0, ''),
(6, 'Effect Template', 'Effects default template', '// In every functions, the variable "effect" defines the effect who uses the script.\r\n\r\n/*\r\n	Called when applied on an entity\r\n*/\r\nfunction onApply(effect) {}\r\n\r\n/*\r\n	Called when removed\r\n*/\r\nfunction onRemove(effect) {}\r\n\r\n/*\r\n	Called on each frame\r\n    \r\n    Parameters:\r\n    	delta - The time between each frame\r\n*/\r\nfunction onUpdate(effect, delta) {}\r\n\r\n/*\r\n	/!\\ Used only when the effect is a "Timer" type effect /!\\\r\n    Called when the timer is finished\r\n*/\r\nfunction onTimerFinished(effect) {}\r\n\r\n\r\n/*\r\n	/!\\ Used only when the effect is a "Tic" type effect /!\\\r\n    Called when a tic is finished\r\n*/\r\nfunction onTicTimerOut(effect) {}', 'Effect', 2, 1, ''),
(7, 'DOT Effect', 'Damages each tick', '// In every functions, the variable "effect" defines the effect who uses the script.\r\n\r\n/*\r\n	Called when applied on an entity\r\n*/\r\nfunction onApply(effect) {/* unused */}\r\n\r\n/*\r\n	Called when removed\r\n*/\r\nfunction onRemove(effect) {/* unused */}\r\n\r\n/*\r\n	Called on each frame\r\n    \r\n    Parameters:\r\n    	delta - The time between each frame\r\n*/\r\nfunction onUpdate(effect, delta) {/* unused */}\r\n\r\n/*\r\n	/!\\ Used only when the effect is a "Timer" type effect /!\\\r\n    Called when the timer is finished\r\n*/\r\nfunction onTimerFinished(effect) {/* unused too */}\r\n\r\n\r\n/*\r\n	/!\\ Used only when the effect is a "Tic" type effect /!\\\r\n    Called when a tic is finished\r\n*/\r\nfunction onTicTimerOut(effect) {\r\n  effect.getEntity().takeDamages((effect.getSkill() == null ? null : effect.getSkill().getCaster()),\r\n                                 dmgVar * effect.getStacks() /* player var? */);\r\n}', 'Effect', 2, 0, '[{"name": "dmgVar", "value": 5, "type": "int", "description": "Used to set tic damages"}]'),
(8, 'DOT Using Intelligence', 'This dot script uses the caster''s intelligence to set damages.', '// In every functions, the variable "effect" defines the effect who uses the script.\r\n\r\n/*\r\n	Called when applied on an entity\r\n*/\r\nfunction onApply(effect) {/* unused */}\r\n\r\n/*\r\n	Called when removed\r\n*/\r\nfunction onRemove(effect) {/* unused */}\r\n\r\n/*\r\n	Called on each frame\r\n    \r\n    Parameters:\r\n    	delta - The time between each frame\r\n*/\r\nfunction onUpdate(effect, delta) {/* unused */}\r\n\r\n/*\r\n	/!\\ Used only when the effect is a "Timer" type effect /!\\\r\n    Called when the timer is finished\r\n*/\r\nfunction onTimerFinished(effect) {/* unused too */}\r\n\r\n\r\n/*\r\n	/!\\ Used only when the effect is a "Tic" type effect /!\\\r\n    Called when a tic is finished\r\n*/\r\nfunction onTicTimerOut(effect) {\r\n  var caster = (effect.getSkill() == null ? null : effect.getSkill().getCaster());\r\n  var dmg = dmgVar;\r\n  if (caster != null) dmg *= (caster.getInt() > 0 ? caster.getInt() : 1);\r\n  effect.getEntity().takeDamages(caster,\r\n                                 dmg * effect.getStacks());\r\n}', 'Effect', 2, 0, '[{"name": "dmgVar", "value": 5, "type": "int", "description": "Used to set tic damages"}]'),
(9, 'Baloon', 'Script for baloon! \\o/', '// In every functions, the variable "entity" defines the entity who uses the script.\r\n\r\n/*\r\n	Called on the creation of the entity\r\n*/\r\nfunction init(entity) {}\r\n\r\n/*\r\n	Called when the entity get damages\r\n	\r\n	Parameters:\r\n		from - The entity from who comes the damages\r\n		amount - The amount of damages\r\n*/\r\nfunction onDamage(entity, from, amount) {}\r\n\r\n/*\r\n	Called when the entity get healed\r\n	\r\n	Parameters:\r\n			from - The entity from who comes the heals\r\n			amount - The amount of heals\r\n*/\r\nfunction onHeal(entity, from, amount) {}\r\n\r\n/*\r\n	Called when an entity enter the parent vision\r\n	\r\n	Parameters:\r\n		entity2 - The entity who is in the vision\r\n*/\r\nfunction onEntityInVision(entity, entity2) {}\r\n\r\n/*\r\n	Called on each loop\r\n	\r\n	Parameters:\r\n		delta - The loop delta value (time passed in ms between each loop)\r\n*/\r\nfunction onUpdate(entity, delta) {}\r\n\r\n/*\r\n	Called when an effect is added to the entity\r\n	\r\n	Parameters:\r\n		effect - The effect added\r\n*/\r\nfunction onEffectAdded(entity, effect) {}\r\n\r\n/*\r\n	Called when an effect is remove from the entity\r\n	\r\n	Parameters:\r\n		effect - The effect removed\r\n*/\r\nfunction onEffectRemoved(entity, effect) {}\r\n\r\n/*\r\n	Called when all effects are simultany cleared\r\n*/\r\nfunction onEffectsCleared(entity) {}\r\n\r\n/*\r\n	Called when an entity taunt our entity\r\n	\r\n	Parameters:\r\n		entity2 - The entity who taunted\r\n*/\r\nfunction onTaunt(entity, entity2) {}\r\n\r\n/*\r\n	Called when a collision occures\r\n	\r\n	Parameters:\r\n		entity2 - The collided entity\r\n*/\r\nfunction onCollision(entity, entity2) {\r\n  // This is a test, and it''s not very effective :(\r\n  // First, let''s calculate the collision angle.\r\n  var collisionAngle = Math.atan2(entity.getPosition().y - entity2.getPosition().y,\r\n                                  entity.getPosition().x - entity2.getPosition().x);\r\n  // Then go to the oposite vector\r\n  entity.goTo(Math.cos(Math.PI - collisionAngle) * 300, Math.sin(Math.PI - collisionAngle) * 300);\r\n}\r\n\r\n', 'LivingEntity', 2, 0, ''),
(10, 'Activable Skill template', 'Activable Skill script template', '// In every functions, the variable "skill" defines the skill who uses the script.\r\n\r\n/*\r\n	Called when the spell is used on xPos and yPos\r\n    \r\n    Params:\r\n    	xPos - xPosition where the spell has been casted\r\n        yPos - yPosition where the spell has been casted\r\n*/\r\nfunction onActivationPosition(skill, xPos, yPos) {}\r\n\r\n/*\r\n	Called when the spell is used on an entity\r\n    \r\n    Params:\r\n    	entity - The cibled entity\r\n*/\r\nfunction onActivationEntity(skill, entity) {}', 'Skill', 2, 1, '');

-- --------------------------------------------------------

--
-- Structure de la table `skills`
--

CREATE TABLE IF NOT EXISTS `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` varchar(100) NOT NULL,
  `passive` tinyint(1) NOT NULL,
  `icon_id` int(11) NOT NULL,
  `range` int(11) NOT NULL,
  `effects_list` varchar(100) NOT NULL,
  `script_id` int(11) NOT NULL,
  `script_vars` text NOT NULL COMMENT 'in json format',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `effects`
--
ALTER TABLE `effects`
  ADD CONSTRAINT `effects_ibfk_1` FOREIGN KEY (`template_id`) REFERENCES `effects_templates` (`id`);

--
-- Contraintes pour la table `effects_templates`
--
ALTER TABLE `effects_templates`
  ADD CONSTRAINT `effects_templates_ibfk_1` FOREIGN KEY (`script_id`) REFERENCES `scripts` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
