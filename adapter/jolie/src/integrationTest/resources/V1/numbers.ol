from console import Console
from string_utils import StringUtils

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

service Test {
  embed Console as console
  embed StringUtils as stringUtils
  embed Numbers as numbers

  main {
    // sumUpTo
    sumUpTo@numbers( 5 ) ( sumUpToRes1 )
    println@console( "sumUpTo Expected 10: " + sumUpToRes1 ) ()

    // sumBetween
    sumBetween@numbers( { m = 2 n = 7 } ) ( sumBetweenRes1 )
    println@console( "sumBetween Expected 20: " + sumBetweenRes1 ) ()
    sumBetween@numbers( { m = 7 n = 2 } ) ( sumBetweenRes1 )
    println@console( "sumBetween Expected 20: " + sumBetweenRes1 ) ()

  }
}