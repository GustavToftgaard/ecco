service Test {
  embed Console(1) as console

  main {
    println@console( "Hello There" )()
  }
}