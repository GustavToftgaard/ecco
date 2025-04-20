service Test {
  embed Console as console
  main {
    println@console( "Hello There" )()
  }
}