local nk = require("nakama")

local function matchmaker_matched(context, matchmaker_users)
    if #matchmaker_users ~= 2 then
      return nil
    end
  
    if matchmaker_users[1].properties["mode"] ~= "authoritative" then
      return nil
    end
    if matchmaker_users[2].properties["mode"] ~= "authoritative" then
      return nil
    end
  
    return nk.match_create("match", {debug = true, expected_users = matchmaker_users})
end
  nk.register_matchmaker_matched(matchmaker_matched)

