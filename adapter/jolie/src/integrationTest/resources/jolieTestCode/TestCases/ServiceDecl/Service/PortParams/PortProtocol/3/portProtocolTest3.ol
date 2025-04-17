service Test {
  inputPort testInPort {
    protocol: http {
      .method = "post"
      }
  }
}