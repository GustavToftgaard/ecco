interface NumbersAPI {
  requestResponse:
    sumUpTo( int )( int ),
    factorial( int )( int )
}

service Numbers {
  execution:sequential

  inputPort numbersInput {
    location: "local"
    interfaces: NumbersAPI
  }

  main {

    [ sumUpTo( n )( response ) {
      for( i = 0, i < n, i++ ) {
        response += i
      }
    } ]

    [ factorial( n )( response ) {
      response = n
      for( i = n - 1, i > 0, i-- ) {
        response *= ( n - i )
      }
    } ]
  }
}
