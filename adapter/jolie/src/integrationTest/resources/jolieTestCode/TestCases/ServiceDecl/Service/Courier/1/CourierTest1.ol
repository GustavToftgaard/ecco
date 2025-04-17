service Test {
  courier <TestInputPort> {
      /* request response */
      [ testOperationRR( request )( response ) ] {
          // some code
      }

      /* one way */
      [ testOperationOW( request ) ] {
          // some code
      }
  }
}