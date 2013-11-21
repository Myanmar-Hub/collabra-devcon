CREATE  TABLE `User` (
  `_id` integer primary key,
  `username` text unique not null,
  `lastLoginTime` integer not null
  );
###
CREATE  TABLE `Conversation` (
  `_id` integer primary key,
  `senderId` integer unique not null,
  `message` integer not null,
  `sendAt` integer not null
  );