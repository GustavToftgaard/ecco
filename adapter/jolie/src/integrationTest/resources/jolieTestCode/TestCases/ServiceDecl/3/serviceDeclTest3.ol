service Test {
  embed Console as console
  embed StringUtils as stringUtils

  execution:sequential

  main {
    println@console( "Hello There" )()
  }
}

service Test2 {
  embed Console as console
  embed StringUtils as stringUtils

  execution:sequential

  main {
    println@console( "General Kenobi" )()
  }
}