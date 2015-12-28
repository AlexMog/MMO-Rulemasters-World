-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2+deb7u2
-- http://www.phpmyadmin.net
--
-- Client: 5.196.154.204
-- Généré le: Lun 28 Décembre 2015 à 13:16
-- Version du serveur: 5.5.44
-- Version de PHP: 5.4.45-0+deb7u2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `eba`
--

-- --------------------------------------------------------

--
-- Structure de la table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `grade` int(11) NOT NULL COMMENT '0 = user, 1 = helper, 2 = moderator, 3 = administrator, 4 = superadmin',
  `time_played` bigint(20) NOT NULL,
  `last_connected` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Contenu de la table `accounts`
--

-- --------------------------------------------------------

--
-- Structure de la table `accounts_activation_keys`
--

CREATE TABLE IF NOT EXISTS `accounts_activation_keys` (
  `key` varchar(255) NOT NULL,
  `account_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `published` tinyint(1) NOT NULL,
  PRIMARY KEY (`key`),
  UNIQUE KEY `key` (`key`),
  KEY `game_id` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `accounts_activation_keys`
--

-- --------------------------------------------------------

--
-- Structure de la table `account_friends`
--

CREATE TABLE IF NOT EXISTS `account_friends` (
  `account_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  KEY `account_id` (`account_id`,`friend_id`),
  KEY `friend_id` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `achivements`
--

CREATE TABLE IF NOT EXISTS `achivements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `games`
--

CREATE TABLE IF NOT EXISTS `games` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `icon_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `games`
--

INSERT INTO `games` (`id`, `name`, `description`, `icon_id`) VALUES
(1, 'Rulemasters World', '', 0);

-- --------------------------------------------------------

--
-- Structure de la table `game_servers`
--

CREATE TABLE IF NOT EXISTS `game_servers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `ip` varchar(100) NOT NULL,
  `port` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `game_servers`
--

INSERT INTO `game_servers` (`id`, `name`, `ip`, `port`) VALUES
(1, 'Localserver', 'localhost', 4243),
(2, 'Test server', '5.196.135.184', 4243);