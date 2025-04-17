service Test ( params : Binding ) {
  embed Console as console
  embed StringUtils as stringUtils

  execution:sequential

  main {
    println@console( "Hello There" )()
  }
}