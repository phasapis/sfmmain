package eu.sifem.model.to;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProcessTO implements AbstractTO{
	private static final long serialVersionUID = -4391484054858917090L;

		private ObjectId _id;
		
		private String projectName;
		
		private String simulationName;
		
		private String workspaceName;
		
		private Boolean isNotStartedPhase;
		
		private Boolean isInQueuePhase;

		private Boolean isDatUnvGenerationPhase;
		
		private Boolean isTriplestoreDataInputPhase;
		
		private Boolean isFinishedPhase;

		private Long processStartTime;
		
		private Long processEndTime;
		
		

		public Boolean getIsInQueuePhase() {
			return isInQueuePhase;
		}

		public void setIsInQueuePhase(Boolean isInQueuePhase) {
			this.isInQueuePhase = isInQueuePhase;
		}

		public ObjectId get_id() {
			return _id;
		}

		public void set_id(ObjectId _id) {
			this._id = _id;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getSimulationName() {
			return simulationName;
		}

		public void setSimulationName(String simulationName) {
			this.simulationName = simulationName;
		}

		public String getWorkspaceName() {
			return workspaceName;
		}

		public void setWorkspaceName(String workspaceName) {
			this.workspaceName = workspaceName;
		}

		public Boolean getIsNotStartedPhase() {
			return isNotStartedPhase;
		}

		public void setIsNotStartedPhase(Boolean isNotStartedPhase) {
			this.isNotStartedPhase = isNotStartedPhase;
		}

		public Boolean getIsDatUnvGenerationPhase() {
			return isDatUnvGenerationPhase;
		}

		public void setIsDatUnvGenerationPhase(Boolean isDatUnvGenerationPhase) {
			this.isDatUnvGenerationPhase = isDatUnvGenerationPhase;
		}

		public Boolean getIsTriplestoreDataInputPhase() {
			return isTriplestoreDataInputPhase;
		}

		public void setIsTriplestoreDataInputPhase(Boolean isTriplestoreDataInputPhase) {
			this.isTriplestoreDataInputPhase = isTriplestoreDataInputPhase;
		}

		public Boolean getIsFinishedPhase() {
			return isFinishedPhase;
		}

		public void setIsFinishedPhase(Boolean isFinishedPhase) {
			this.isFinishedPhase = isFinishedPhase;
		}

		public Long getProcessStartTime() {
			return processStartTime;
		}

		public void setProcessStartTime(Long processStartTime) {
			this.processStartTime = processStartTime;
		}

		public Long getProcessEndTime() {
			return processEndTime;
		}

		public void setProcessEndTime(Long processEndTime) {
			this.processEndTime = processEndTime;
		}
		
		
}
