
class DacapoBenchmarkAnalyzer {

  String dacapoJarPath
  String gcViewerJarPath
  String benchmarkName
  String dataFolderPath

  DacapoBenchmarkAnalyzer(String dacapoJarPath, String gcViewerJarPath, String benchmarkName,String dataFolderPath) {
    this.dacapoJarPath = dacapoJarPath
    this.gcViewerJarPath = gcViewerJarPath
    this.benchmarkName = benchmarkName
    this.dataFolderPath = dataFolderPath

  }

  def void analyzeBenchmark(String ... args){
    args.each { String arg ->
      run(arg)
    }
    cleanRun()
  }

  public void cleanRun() {
    run(null)
  }

  public void run(String arg) {
    String suffix = arg==null?"":"|${arg}"
    String dacapoProcessString = "java -jar -Xloggc:${dataFolderPath}/logs/${benchmarkName}${suffix}.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps ${dacapoJarPath} ${benchmarkName}"
    String gcLogViewerProcessString = "java -jar ${gcViewerJarPath} ${dataFolderPath}/logs/${benchmarkName}${suffix}.log ${dataFolderPath}/data/${benchmarkName}${suffix}.csv -t SUMMARY"

    println "... executing $benchmarkName ..."

    def dacapoProcess = dacapoProcessString.execute();
    dacapoProcess.waitFor()

    def gcLogViewerProcess = gcLogViewerProcessString.execute();
    gcLogViewerProcess.waitFor()
  }
}
