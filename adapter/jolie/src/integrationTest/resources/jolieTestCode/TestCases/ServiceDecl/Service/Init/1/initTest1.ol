service Test {
  init {
    config.database << {
      username = ""
      password = ""
      host = ""
      database = "file:db.sqlite"
      driver = "sqlite"
    }
  }
}