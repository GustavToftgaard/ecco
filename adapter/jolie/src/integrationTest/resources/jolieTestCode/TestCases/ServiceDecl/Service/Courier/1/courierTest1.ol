service Test {
  courier <TestInputPort> {
    [ testOperationRR( request )( response ) ] {
      some code
    }

    [ testOperationOW( request ) ] {
      some code
    }
  }
}