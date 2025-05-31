type twoArguments {
  m:int
  n:int
}

interface NumbersAPI {
  requestResponse:
    factorial( int )( int ),
    sumBetween( twoArguments )( int )
}

service Numbers {
  execution:sequential

  inputPort numbersInput {
    location: "local"
    interfaces: NumbersAPI
  }

  main {

    [ factorial( n )( response ) {
      response = n
      for( i = n - 1, i > 0, i-- ) {
        response *= ( n - i )
      }
    } ]

    [ sumBetween( request )( response ) {
      m -> request.m
      n -> request.n

      upToN = n * ( n - 1 ) / 2
      upToM = m * ( m - 1 ) / 2

      if( m < n ) {
        response = upToN - upToM
      } else {
        response = upToM - upToN
      }
    } ]
  }
}
