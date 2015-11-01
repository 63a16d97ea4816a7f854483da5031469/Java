/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : live
Target Host     : localhost:3306
Target Database : live
Date: 2014-10-18 12:40:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for detail
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of detail
-- ----------------------------

-- ----------------------------
-- Table structure for images
-- ----------------------------
DROP TABLE IF EXISTS `images`;
CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detailpageid` int(11) NOT NULL,
  `piclink` varchar(255) NOT NULL,
  `localpath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `detailpic` (`detailpageid`),
  CONSTRAINT `detailpic` FOREIGN KEY (`detailpageid`) REFERENCES `detail` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of images
-- ----------------------------

-- ----------------------------
-- Table structure for prerooms
-- ----------------------------
DROP TABLE IF EXISTS `prerooms`;
CREATE TABLE `prerooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of prerooms
-- ----------------------------
INSERT INTO `prerooms` VALUES ('3', '宏茂桥5道603有男生床位全包. 有单间。', ' 10-14 ', '90615605', '230.00', 'http://www.shichengzufang.com/info/view/id/19852');
INSERT INTO `prerooms` VALUES ('4', '布莱德地铁站附近有男女床位出租走路二分钟，新房子，10月16号以后可以入住。', ' 10-14 ', '83933059', '250.00', 'http://www.shichengzufang.com/info/view/id/19482');
INSERT INTO `prerooms` VALUES ('5', 'Serangoon Ave 3,Blk 332 1 Common Room', ' 10-14 ', '81413820', '450.00', 'http://www.shichengzufang.com/info/view/id/28660');
INSERT INTO `prerooms` VALUES ('6', '波东巴西普通房，随时入住，单间公寓好房', ' 10-14 ', '97575026', 'null', 'http://www.shichengzufang.com/info/view/id/10657');
INSERT INTO `prerooms` VALUES ('7', '盛港地铁站公寓出租 普通房 主人房 佣人房', ' 10-14 ', '86207250', 'null', 'http://www.shichengzufang.com/info/view/id/3757');
INSERT INTO `prerooms` VALUES ('8', '义顺◆BLK115◆◆4+1◆◆非中介◆◆有冷气◆', ' 10-14 ', '97575026', 'null', 'http://www.shichengzufang.com/info/view/id/25286');
INSERT INTO `prerooms` VALUES ('9', '牛车水主人房，非中介，超大独立卫生间', ' 10-14 ', '97575026', 'null', 'http://www.shichengzufang.com/info/view/id/12097');
INSERT INTO `prerooms` VALUES ('10', '巴耶利巴地铁3分钟泳池公寓主人房长短期日租', ' 10-14 ', '91915395', '1300.00', 'http://www.shichengzufang.com/info/view/id/26180');
INSERT INTO `prerooms` VALUES ('11', '无中介费 麦波申MACPHERSON附近洋房大普通房 家具空调网络', ' 10-14 ', '91860119', '1000.00', 'http://www.shichengzufang.com/info/view/id/28659');
INSERT INTO `prerooms` VALUES ('12', '三巴旺大牌306主人房出租', ' 10-14 ', '94386967', '800.00', 'http://www.shichengzufang.com/info/view/id/23054');
INSERT INTO `prerooms` VALUES ('13', '欲郎东大牌238有男床位出租', ' 10-14 ', '84263446', 'null', 'http://www.shichengzufang.com/info/view/id/19766');
INSERT INTO `prerooms` VALUES ('14', '2分钟到欧南园地铁站漂亮公寓招男女搭房', ' 10-14 ', '82961430', 'null', 'http://www.shichengzufang.com/info/view/id/22628');
INSERT INTO `prerooms` VALUES ('15', '中巴鲁男搭房出租', ' 10-14 ', '90752866', 'null', 'http://www.shichengzufang.com/info/view/id/26643');
INSERT INTO `prerooms` VALUES ('16', '海军部温馨之家!主人房S$800，近地铁，住人少，可以煮，安静方便', ' 10-14 ', '81410802', '800.00', 'http://www.shichengzufang.com/info/view/id/28563');
INSERT INTO `prerooms` VALUES ('17', '裕廊西先驱地铁站708招女搭房，主人房，包水电网，有冷气，宽敞明亮，交通方便', ' 10-14 ', '97733391', 'null', 'http://www.shichengzufang.com/info/view/id/28658');
INSERT INTO `prerooms` VALUES ('18', '海军部口福附近大主人房出租，高楼，通风，凉快，安静，超干净', ' 10-14 ', '92349420', 'null', 'http://www.shichengzufang.com/info/view/id/24078');
INSERT INTO `prerooms` VALUES ('19', '海军部口福附近钟点房、或日租-高楼，人少，超极干净', ' 10-14 ', '92349420', '30.00', 'http://www.shichengzufang.com/info/view/id/24925');
INSERT INTO `prerooms` VALUES ('20', '求 男搭房', ' 10-14 ', '82990234', '400.00', 'http://www.shichengzufang.com/info/view/id/27832');
INSERT INTO `prerooms` VALUES ('21', 'Bukit Batok BLK204 #11-10', ' 10-14 ', '83485769', '700.00', 'http://www.shichengzufang.com/info/view/id/28657');
INSERT INTO `prerooms` VALUES ('22', 'BLK 690 HOUGANG STREET 61 “角头”大普通房出租 包冷', ' 10-14 ', '97913126', '720.00', 'http://www.shichengzufang.com/info/view/id/28656');
INSERT INTO `prerooms` VALUES ('23', '淡滨泥新装修大型豪华公寓TheTropica Condo(淡马锡Poly隔壁)!', ' 10-14 ', '96880795', '880.00', 'http://www.shichengzufang.com/info/view/id/26126');
INSERT INTO `prerooms` VALUES ('24', '淡滨泥新装修豪华公寓大普通房，（免费300兆光纤高速网络）', ' 10-14 ', '96880795', '48.00', 'http://www.shichengzufang.com/info/view/id/26127');
INSERT INTO `prerooms` VALUES ('25', '五分钟走路兀兰地铁站招男女搭房-200全包 随时入住 方便自由', ' 10-14 ', '86201336', '180.00', 'http://www.shichengzufang.com/info/view/id/26699');
INSERT INTO `prerooms` VALUES ('26', '步行4分钟海军部地铁站, 一间租屋普通房出租', ' 10-14 ', '91736810', 'null', 'http://www.shichengzufang.com/info/view/id/27986');
INSERT INTO `prerooms` VALUES ('27', '马西领大牌134大主人房出租招女搭房', ' 10-14 ', '87000512', '230.00', 'http://www.shichengzufang.com/info/view/id/28154');
INSERT INTO `prerooms` VALUES ('28', '勿洛水池路 大牌615 多个床位出租 有冷气 冰箱 洗衣机 而且水电网全包', ' 10-14 ', '86951366', 'null', 'http://www.shichengzufang.com/info/view/id/28655');
INSERT INTO `prerooms` VALUES ('29', '步行7分钟到盛港 地铁站 ， 招一名 女生 搭房', ' 10-14 ', '82992532', 'null', 'http://www.shichengzufang.com/info/view/id/26629');
INSERT INTO `prerooms` VALUES ('30', '公寓房间出租 2分钟到阿裕尼地铁站', ' 10-14 ', '91989105', '850.00', 'http://www.shichengzufang.com/info/view/id/15790');
INSERT INTO `prerooms` VALUES ('31', 'lentor green排屋男女床位出租全包.有单间。', ' 10-14 ', '90615605', '600.00', 'http://www.shichengzufang.com/info/view/id/28060');
INSERT INTO `prerooms` VALUES ('32', 'lentor green排屋男女床位出租全包.有单间。', ' 10-14 ', '90615605', '260.00', 'http://www.shichengzufang.com/info/view/id/27524');
INSERT INTO `prerooms` VALUES ('33', '宏茂桥5道603有男生床位全包. 有单间。', ' 10-14 ', '90615605', '230.00', 'http://www.shichengzufang.com/info/view/id/19852');

-- ----------------------------
-- Table structure for sourcecode
-- ----------------------------
DROP TABLE IF EXISTS `sourcecode`;
CREATE TABLE `sourcecode` (
  `id` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `sourcecode` varchar(255) DEFAULT NULL,
  `datetime` varchar(255) NOT NULL,
  `sourceweb` varchar(255) DEFAULT NULL,
  `lengthofcode` varchar(255) DEFAULT NULL,
  `dateofcontent` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sourcecode
-- ----------------------------
