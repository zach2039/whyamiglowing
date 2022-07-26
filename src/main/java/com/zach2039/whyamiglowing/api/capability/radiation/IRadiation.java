package com.zach2039.whyamiglowing.api.capability.radiation;

public interface IRadiation {

	float getAbsorbedDoseMillirems();
	void setAbsorbedDoseMillirems(float absorbedDoseMillirems);

	void increaseAbsorbedDoseMillirems(float absorbedDoseMillirems);
	void increaseAbsorbedDoseMilliremsBypassExternalResistances(float absorbedDoseMillirems);
	void decreaseAbsorbedDoseMillirems(float absorbedDoseMillirems);

	float getRadiationResistance();
	void setRadiationResistance(float radiationResistance);

	float getInternalRadiationResistance();
	void setInternalRadiationResistance(float internalRadiationResistance);

	float getCurrentTotalExposureMilliremsPerSecond();

	float getCurrentExternalExposureMilliremsPerSecond();
	void setCurrentExternalExposureMilliremsPerSecond(float currentExposureMilliremsPerSecond);

	float getLastExternalExposureMilliremsPerSecond();
	void setLastExternalExposureMilliremsPerSecond(float lastExposureMilliremsPerSecond);

	float getContaminationMilliremsPerSecond();
	void setContaminationMilliremsPerSecond(float contaminationMilliremsPerSecond);

	void increaseContaminationMilliremsPerSecond(float contaminationMilliremsPerSecond);
	void decreaseContaminationMilliremsPerSecond(float contaminationMilliremsPerSecond);
}
