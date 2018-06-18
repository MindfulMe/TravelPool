local nk = require("nakama")
nk.match_create('baccarat')

local function initialize_user(context, _payload)
  local value = {
    coins = 1000,
    gems = 10,
    artifacts = 0
  }
  --[[local record = {
    collection = "wallets",
    record = "mywallet",
    user_id = context.user_id,
    value = value,
    version = "*"   -- only write record if one doesn't already exist.
  }
  pcall(nk.storage_write, { record }) -- write record, ignore errors. --]]

  nk.logger_info(("User id:%q"):format( nk.json_encode(context)))
  nk.logger_info(("User id:%q"):format( nk.json_encode(_payload)))

  local status, err = pcall(nk.wallet_update, context.user_id, value)

  if(not status) then
    nk.logger_info(("Error:%q"):format(err))
  end

  return _payload
    
end

-- change to whatever message name matches your authentication type.
nk.register_req_before(initialize_user, "GetAccount")
