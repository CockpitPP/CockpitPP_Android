----------------------------------------------------------------------------------
--
--                          []                         []
--                          ||   ___     ___     ___   ||
--                          ||  /   \   /| |\   /   \  ||
--                          || |  O  |__|| ||__|  O  | ||
--                          ||  \___/--/^^^^^\--\___/  ||
--                      __  ||________|       |________||  __
--   .-----------------/  \-++--------|   .   |--------++-/  \-----------------.
--  /.---------________|  |___________\__(*)__/___________|  |________---------.\
--            |    |   '$$'   |                       |   '$$'   |    |
--           (o)  (o)        (o)                     (o)        (o)  (o)
--
-- Because we all love the BBBBBBBBRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRTTTTTTTTTTTTTT
--
-- AsciiArt A-10 Thunderbolt II from: Mike Whaley, Georgia Tech, 
-- source: http://xcski.com/~ptomblin/planes.txt
----------------------------------------------------------------------------------





----------------------------------------------------------------------------------
--Pilot, please edit only these three lines
----------------------------------------------------------------------------------
 -- PUT YOUR ANDROID IP(S) HERE, you will find it in the app:
local clientIP={"192.168.0.10", "192.168.0.14"} --Several Android devices, for only 1 device enter: local clientIP={"192.168.0.10"}

--Editable but not mandatory, put them in the app
local DCS_PORT = 14801
local ANDROID_PORT = 14800
----------------------------------------------------------------------------------






----------------------------------------------------------------------------------
--Developers, if you know what you are doing, feel free to change things here
----------------------------------------------------------------------------------
local log_file = nil
local lengthIPTable = 0
local ipUsed = 1
local DELAY = 5
local POSITION = 0

local HEAD_MSG = "Cockpit++"
local msgOut =""

local prevLuaExportStart = LuaExportStart


function LuaExportStart()

	if prevLuaExportStart then
        prevLuaExportStart()
    end
	
	lengthIPTable = table.getn(clientIP)

	log_file = io.open(lfs.writedir().."/Logs/Export.log", "w")
	
	log_file:write("Opening file")
	log_file:write("\n")
	--log_file:write(lengthIPTable)
	--log_file:write("\n")

	package.path  = package.path..";"..lfs.currentdir().."/LuaSocket/?.lua"
  	package.cpath = package.cpath..";"..lfs.currentdir().."/LuaSocket/?.dll"
 	
 	socket = require("socket")
 	udp = socket.udp()
	udp:setsockname("*", DCS_PORT)
	udp:setoption('broadcast', true)
	udp:settimeout(0)
end


local RELEASE = 0
local TYPEBUTTON = 0
local DEVICE = 0
local COMMAND = 0
local VALUE = 0

function LuaExportBeforeNextFrame()
	
	if RELEASE == 1 then
		RELEASE = 0
		GetDevice(DEVICE):performClickableAction(COMMAND,1*0)
		
	else
		data, ip, port = udp:receivefrom()
		
		if data then
			--log_file:write("---------------------------------------------------\n")
			--log_file:write("Received: ", data)
			--log_file:write("\n")
			--clientIP = ip
	  
			local dataArray = string.gmatch(data, '([^,]+)')

			if dataArray(1)==HEAD_MSG then

				TYPEBUTTON = dataArray(2)
				DEVICE = dataArray(3)
				COMMAND = dataArray(4)
				VALUE = dataArray(5)
				
				--log_file:write(TYPEBUTTON,"\n")
				--log_file:write(DEVICE,"\n")
				--log_file:write(COMMAND,"\n")
				--log_file:write(VALUE,"\n")
				
				if TYPEBUTTON == "1" then
						GetDevice(DEVICE):performClickableAction(COMMAND,VALUE)
				end
				
				if TYPEBUTTON == "2" then
						GetDevice(DEVICE):performClickableAction(COMMAND,VALUE)
				end
				
				if TYPEBUTTON == "3" then
						GetDevice(DEVICE):performClickableAction(COMMAND,VALUE)
				end
				
				if TYPEBUTTON == "4" then
						GetDevice(DEVICE):performClickableAction(COMMAND,VALUE)
				end
				
				if TYPEBUTTON == "5" then
						RELEASE = 1
						GetDevice(DEVICE):performClickableAction(COMMAND,VALUE)
				end
				
				if TYPEBUTTON == "6" then
						GetDevice(DEVICE):performClickableAction(COMMAND,VALUE)
				end
				
				if TYPEBUTTON == "7" then
						GetDevice(DEVICE):performClickableAction(COMMAND,VALUE)
				end
			end    
		end
	end

	
end



function LuaExportAfterNextFrame()
	POSITION = POSITION + 1

	if(POSITION == DELAY) then
		local selfData = LoGetSelfData()
		if selfData then
			currentAircraft = selfData["Name"]
			--log_file:write(acftName)
			
			msgOut = HEAD_MSG ..",".. currentAircraft .. ","

			if currentAircraft == "M-2000C" then
				local MainPanel = GetDevice(0)

				pca = MainPanel:get_argument_value(234) ..";".. MainPanel:get_argument_value(463) ..";".. MainPanel:get_argument_value(249) ..";".. MainPanel:get_argument_value(248) ..";".. MainPanel:get_argument_value(236) ..";".. MainPanel:get_argument_value(238) ..";".. MainPanel:get_argument_value(240) ..";".. MainPanel:get_argument_value(242) ..";".. MainPanel:get_argument_value(244) ..";".. MainPanel:get_argument_value(246) ..";".. MainPanel:get_argument_value(247) ..";".. MainPanel:get_argument_value(251) ..";".. MainPanel:get_argument_value(252) ..";".. MainPanel:get_argument_value(254) ..";".. MainPanel:get_argument_value(255) ..";".. MainPanel:get_argument_value(257) ..";".. MainPanel:get_argument_value(258) ..";".. MainPanel:get_argument_value(260) ..";".. MainPanel:get_argument_value(261) ..";".. MainPanel:get_argument_value(263) ..";".. MainPanel:get_argument_value(264)

				ppa = MainPanel:get_argument_value(276) ..";".. MainPanel:get_argument_value(265) ..";".. MainPanel:get_argument_value(277) ..";".. MainPanel:get_argument_value(278) ..";".. MainPanel:get_argument_value(275) ..";".. MainPanel:get_argument_value(267) ..";".. MainPanel:get_argument_value(268) ..";".. MainPanel:get_argument_value(270) ..";".. MainPanel:get_argument_value(271) ..";".. MainPanel:get_argument_value(273) ..";".. MainPanel:get_argument_value(274) ..";".. MainPanel:get_argument_value(280) ..";".. MainPanel:get_argument_value(281)
				
				msgOut = msgOut..list_indication(6)..","..list_indication(7)..","..pca..","..list_indication(8)..","..ppa.." \n"
				--list_indication(1) VTB
				--list_indication(2) RWR
				--list_indication(3) ECM_CHF
				--list_indication(4) ecm flr
				--list_indication(5) fuel
				--list_indication(6) PCA UR
				--list_indication(7) PCA BR
				--list_indication(8) PPA
				--list_indication(9) frequency
				--list_indication(10) frequency(PCN ?)
				--list_indication(11) PCN BR
				--list_indication(12) nothing....s
				--list_indication(13) base
				--list_indication(14) many data
				--log_file:write(list_indication(2))
			end
			if currentAircraft == "F-15C" then
			--LoGetTWSInfo() -- return Threat Warning System status (result  the table )
			--result_of_LoGetTWSInfo =
			--{
			--	Mode = , -- current mode (0 - all ,1 - lock only,2 - launch only
			--	Emitters = {table of emitters}
			--}
			--emitter_table =
			--{
			--	ID =, -- world ID
			--	Type = {level1,level2,level3,level4}, -- world database classification of emitter #the different levels of types - for instance, level1 (the first int), means airborne (1), land(2), ship(3), etc
			--	Power =, -- power of signal
			--	Azimuth =,
			--	Priority =,-- priority of emitter (int)
			--	SignalType =, -- string with vlues: "scan" ,"lock", "missile_radio_guided","track_while_scan";
			--}
				local result_of_LoGetTWSInfo = LoGetTWSInfo()
				local data =""
				local allSpots =""
				local spot =""
				local name =""
				if result_of_LoGetTWSInfo then
					for k,emitter_table in pairs (result_of_LoGetTWSInfo.Emitters) do
						name = LoGetNameByType(emitter_table.Type.level1,emitter_table.Type.level2,emitter_table.Type.level3,emitter_table.Type.level4)
						if name == nil or name == '' then
							name = "UK"
						end
						spot = name .. ":" .. emitter_table.Power .. ":" .. emitter_table.Azimuth .. ":" .. emitter_table.Priority .. ":" .. emitter_table.SignalType .. ":" .. emitter_table.Type.level1 .. ":" .. emitter_table.Type.level2 .. ":" .. emitter_table.Type.level3 .. ":" .. emitter_table.Type.level4
						allSpots = allSpots .. ";" .. spot
					end
					data = result_of_LoGetTWSInfo.Mode .. ",".. allSpots		
				end
				msgOut = msgOut..data
			end
			
			--log_file:write("\n")
			--log_file:write(msgOut)
			--log_file:write("\n")
			
			--Sending data to device
			udp:sendto(msgOut, clientIP[ipUsed], ANDROID_PORT)
			
			
			--To alternate data transmission between several devices
			if ipUsed == lengthIPTable then
				ipUsed = 1
			else
				ipUsed = ipUsed + 1
			end
		end
		POSITION = 0
	end
end


function LuaExportStop()
   if log_file then
   	log_file:write("Closing log file...")
   	log_file:close()
   	log_file = nil
   end
end
----------------------------------------------------------------------------------






--
--
--     _         Spitfire
--   |   \       Murray "Moray" Lalor
--  | |    \
-- |  |     \                               __---___
-- |  |      \ _____________---------------^      | ^\
--  | | =======--_               ___     \________|__*^--------------__________
--   ^-____                    /  _  \    Capt. Moray  #######     ********  | \
--        * -----_______      (  (_)  )                ###                  _ -'
--                      -------\____ /                                __ - '
--                                   -----======________:----------- '
--
--
-- source: http://xcski.com/~ptomblin/planes.txt






