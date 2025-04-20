service Test {
  embed Console(1) as console
  embed StringUtils(1) as stringUtils
  embed MyService( { .protocol = "sodep", .factor = 2 } ) as Service
}