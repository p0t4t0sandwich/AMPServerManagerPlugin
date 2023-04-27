# PanelServerManagerPlugin

## About

A plugin that allows you to manage your Panel's game servers from within minecraft. This plugin is still in development and is not quite ready for production use. Feel free to mess around with it and report any bugs you find, or any features you would like to see.

If you need to port this mod/plugin to an unsupported platform/version, feel free to create an issue, and I'll see what I can do. Older MC implementations are built on an as-needed basis.

If your panel isn't supported yet, create an issue, and we can do some digging to see if it's possible.

## Commands

### /psm

`/psmb` for BungeeCord

`/psmc` for Client-side

```
# General
start <server>
stop <server>
restart <server>
kill <server>
send <server> <command>
status <server>

# AMP only
sleep <server>
players <server>
backup <server> [name] [description] [sticky <- true or false]
```

## Permissions

```
psm -- Allows access to all commands
```

## Configuration

### CubeCoders AMP

#### ADS
```yaml
---
panels:
  <panel name>:
    type: cubecodersamp
    host: http://localhost:8080/
    username: admin
    password: admin
```

#### Servers

```yaml
servers:
  # Server using the ADS for authentication
  <server name>:
    panel: <panel name>
    # Instance Name -- found in AMP
    name: Minecraft01
    # instance ID -- right click and manage in new tab, this will be the bit after the ?id= in the URL
    # The plugin can also fetch this automatically if you leave it blank
    id: 1ty3j38u

  # Server using the instance's web port for authentication
  <server name 2>:
    panel: ampinstance
    host: http://localhost:8081/
    username: admin
    password: admin
```

### Groups

```yaml
# Experimental feature, still a work in progress
groups:
  <group name>:
    name: Group1
    servers:
      - <server name>
      - <server name>
    tasks:
      sendboop:
        command: send {server} say boop!
        interval: 60 # in seconds
        conditions:
          - placeholder: playercount
            operator: ==
            value: 0
```

#### Placeholders

- playercount -- the number of players on the server

## TODO:

Contributions and suggestions are welcome! Just open an issue or a pull request, and I'll get to it as soon as I can.

### Bugs

- [x] Program doesn't time out if it can't connect to the AMP API -- is this a problem with ampapi-java? -- If so, then I'll have to fix it there
- [x] Another instance of this problem: occurs when the instance is offline, replicates the above behavior just at an instance level

### General

- [x] Refactor everything -- this is a monolithic mess
- [ ] Add a command to reload the config
- [x] Design an API for other plugins to use -- that way this plugin doesn't become a spaghetti mess
- [ ] Forge support -- try and generalize the fabric command and see if I can get it to work with forge, as both use brigadier
- [ ] Multi-Fabric version support -- some gradle project voodoo required
- [ ] Fabric client side chat listener -- do it like this? https://www.reddit.com/r/fabricmc/comments/wg7jrx/onchat/
- [ ] Quilt support
- [ ] Sponge support
- [ ] Velocity support
- [ ] Set up proper gradle projects for each platform -- need help with this

### Permissions

- [ ] Design a permission scema -- `psm.<command>.<server>` - `psm.<command>.<group>`
- [ ] Add permissions for each command -- `psm.command.<command>`
- [ ] Add permissions for each command for each server -- `psm.<command>.<server>`
- [ ] Add permissions for each command for each group -- `psm.<command>.<group>`

- [ ] Set up dynamic permissions checks
  - [ ] Bukkit and BungeeCord will be easy, just use the `hasPermission` method
  - [ ] Fabric will be more fun to implement -- either look into how Fabric does it, or require LuckPerms to doll out specific permissions
  - [ ] Similar dealio for Forge

### Commands

- [ ] Set up group commands -- `/psm group <group> <command>`
- [ ] Add commands to manage/add to the config from within the game
  - [ ] Add a command to list all servers -- `/psm server list`
  - [ ] Add commands to add/remove servers

  - [ ] Add a command to list all groups -- `/psm group list`
  - [ ] Add commands to add/remove servers from groups
    - [ ] `/psm group <group> server add <server name>`
    - [ ] `/psm group <group> server remove <server name>`

  - [ ] Add a command to list all tasks in a group -- `/psm group <group> task list`
  - [ ] Add commands to add/remove tasks from groups
    - [ ] `/psm group <group> task add <task name> <command> <interval> <conditions>`
    - [ ] ^A parser to make that possible
    - [ ] `/psm group <group> task remove <task name>`
    - [ ] `/psm group <group> task cancel <task name>`
  - [ ] findplayer command -- `/psm group <group> findplayer <player>`

  - [ ] Add a command to list all conditionals in a task -- `/psm group <group> task <task> condition list`

- [x] playerlist command -- `/psm players <server>`

### Groups

- [ ] Finalize configuration for groups
  - [ ] Add timed tasks
  - [ ] Add conditionals
  - [ ] think of a placeholder spec for conditionals
  - [ ] Build a parser for conditionals and placeholders
  - [ ] Webhook task
- [ ] Send commands to all servers in a group
- [ ] Group playerlist interface -- Some readout of the playerlist for all servers in a group -- good for Proxy setups

### Misc

- [x] Add multi-ADS/Controller logic -- similar to old WatchFerret

- [ ] Update mods/plugins from url? -- maybe add a `update` command to the group system?
  - [ ] Optional: include regex to delete old files

- [ ] Server console regex task -- maybe add a `regex` command to the group system?

- [ ] No-start status fix -- plop a proper `server started` message in the console -- Fix for Forge 1.12.2 v14.23.5.2858 and FTB Revelation

- [x] Make AMPServerManagerPlugin function as a standalone terminal program, since it's all compiled to Java 8 anyway.
  - [x] Format the log properly
  - [x] Add pretty terminal colors
  - [ ] Wonder how hard tab completion and command history would be to implement

- [ ] Add the ability to sync the config with a database? -- would need one AMPServerManager process to act as a main process and handle all the group tasks.
  - [ ] hot reload the config when it changes -- how? -- LuckPerms does it, maybe subscribe to database changes?

- [ ] WatchFerret?
  - [ ] Should be able to hook into the Groups system and run the checks on a timer -- maybe add a `watch` command to the group system? -- Store in Groups class in some sort of variables property