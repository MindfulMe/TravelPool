local du = require("debug_utils")
local nk = require("nakama")

local function match_init(context, params)
  local state = {
    debug = (params and params.debug) or false
  }
  print("match init context:\n" .. du.print_r(context) .. "match init params:\n" .. du.print_r(params))
  nk.logger_info(("MatchInfo:%q"):format(context))
  local tick_rate = 1 
  local label = "add_4_cards"

  return state, tick_rate, label
end

local function match_join_attempt(context, dispatcher, tick, state, presence)
  print("match join attempt:\n" .. du.print_r(presence))
  return state, true
end

local function match_leave(context, dispatcher, tick, state, presences)
  print("match leave:\n" .. du.print_r(presences))
  return state
end

print("Loop will be called in every 30 seconds... ")

local function match_loop(context, dispatcher, tick, state, messages)
  math.randomseed(os.time())
  local clock = os.clock
  function sleep(n)  -- seconds
   local t0 = clock()
   while clock() - t0 <= n do
   end
  end
  
  print("match " .. context.match_id .. " tick " .. tick)
  -- print("match " .. context.match_id .. " messages:\n" .. du.print_r(messages))
  -- bet logic start
  local payout = 0
  local bets = {
    banker = 0,
    player = 0,
    tie = 0,
    player_pair = 0,
    banker_pair = 0
  }

  local bet_types = {
    is_banker = true,
    is_player = false,
    is_tie = false,
    is_player_pair = false,
    is_banker_pair = false
  }

  -- bet deflogic end
  cards = {}    -- new array
  local winners = ""  -- handle draw or win of just a player/banker
  local _is_player_pair = false
  local _is_banker_pair = false

  
  for i=0, 11 do
    cards[i] = math.random(0, 11)
    print("Card[" .. i .. "]:" .. cards[i])
  end
  
  local player_sum = cards[0] + cards[2] -- sum of player points
  local banker_sum = cards[1] + cards[3] -- sum of banker points

  if player_sum < 6 then
    player_sum = player_sum + cards[4]
  end

  if banker_sum < 3 then 
    banker_sum = banker_sum + cards[5]
  end  

  if banker_sum == 3 and player_sum < 6 and cards[4] == 8 then
  else
    banker_sum = banker_sum + cards[6]
  end

  if banker_sum == 4 and player_sum < 6 and (cards[4] == 8 or cards[4] == 9 or cards[4] == 0) then
  else
    banker_sum = banker_sum + cards[7]
  end

  if banker_sum == 5 and player_sum < 6 and (cards[4] == 1 or cards[4] == 2 or cards[4] == 3 or cards[4] == 8 or cards[4] == 9 or cards[4] == 0) then
  else
    banker_sum = banker_sum + cards[9]
  end

  if banker_sum == 4 and player_sum < 6 and (cards[4] == 7 or cards[4] == 6) then
  else
    banker_sum = banker_sum + cards[10]
  end

  if cards[0] == cards[2] then 
    _is_player_pair = true
  elseif cards[1] == cards[3] then 
    _is_banker_pair = true 
  end

  print("Player sum " .. player_sum)
  print("Banker sum " .. banker_sum)

  if player_sum > 9 then
  player_sum = player_sum % 10
  elseif banker_sum > 9 then
  banker_sum = banker_sum % 10
  end  

  if banker_sum == player_sum then 
    winners = 'tie'
  elseif player_sum == 8 or player_sum == 9 or player_sum > banker_sum then
    winners = 'player'
  elseif banker_sum == 8 or banker_sum == 9 or banker_sum > player_sum then
    winners = 'banker'
  else
    winners = ''
  end

  if bet_types.banker and  winners == 'banker' then 
    payout = 0.95
  elseif bet_types.is_player_pair and _is_player_pair == true then 
    payout = 11
  elseif bet_types.is_banker_pair and _is_banker_pair == true then 
    payout = 11
  elseif bet_types.banker and winners == 'player' then 
    payout = -1
  elseif bet_types.banker and winners == 'tie' then 
    payout = 0
  elseif bet_types.player and winners == 'banker' then 
    payout = -1
  elseif bet_types.player and winners == 'player' then 
    payout = 1
  elseif bet_types.player and winners == 'tie' then
    payout = 0
  elseif bet_types.tie and winners == 'banker' then 
    payout = -1
  elseif bet_types.tie and winners == 'player' then 
    payout = -1
  elseif bet_types.tie and winners == 'tie' then
    payout = 8
  else
    payout = 0
  end  
  
  local record = {
    collection = "game", 
    key = "bacca",
    player_cards = {cards[0], cards[2]},
    banker_cards = {cards[1], cards[3]},
    winner = winners,
    wager = payout,
    permission_read = 2, 
    permission_write = 1
  } -- A = 1, K = 0 , Q = 0 , J = 0 // Card suits have no meaning

  print("winner: " .. record.winner)
  print("Payout: " .. record.wager)
  print("Key: ".. record.key)
  pcall(nk.storage_write, { record })
  -- nk.storage_write(record)
  
  sleep(2)

  local object_ids = {
    {collection = "game", key = "bacca", user_id = nil},
  }

  local objects = nk.storage_read(object_ids)
  printf(objects)


  return state
end

-- Match modules must return a table with these functions defined. All functions are required.
return {
  match_init = match_init,
  match_join_attempt = match_join_attempt,
  match_leave = match_leave,
  match_loop = match_loop
}
