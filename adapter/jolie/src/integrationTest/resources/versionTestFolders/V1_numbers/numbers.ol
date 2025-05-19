type twoArguments {
  m:int 
  n:int 
}

interface NumbersAPI { 
  requestResponse: 
    sumUpTo( int )( int ),
    sumBetween( twoArguments )( int )
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

    [ sumBetween( request )( response ) {
      m -> request.m
      n -> request.n

      upToN = n * ( n - 1 ) / 2
      upToM = m * ( m - 1 ) / 2
      response = upToN - upToM

      if( m < n ) {
        response = upToN - upToM
      } else {
        response = upToM - upToN
      }
    } ]
  }
}
