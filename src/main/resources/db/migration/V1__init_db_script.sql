CREATE TABLE `espdata` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apMacAddress` varchar(255) DEFAULT NULL,
  `chipSize` varchar(255) DEFAULT NULL,
  `freeSpace` varchar(255) DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `macAddress` varchar(255) NOT NULL,
  `md5NewVersion` varchar(255) DEFAULT NULL,
  `newVersion` varchar(255) DEFAULT NULL,
  `sdkVersion` varchar(255) DEFAULT NULL,
  `sketchSize` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;