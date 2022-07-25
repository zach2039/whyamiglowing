package com.zach2039.whyamiglowing.api.capability.radiation;

public interface IRadiation {

	float getAbsorbedDoseMillirems();
	void setAbsorbedDoseMillirems(float absorbedDoseMillirems);

	void increaseAbsorbedDoseMillirems(float absorbedDoseMillirems);
	void decreaseAbsorbedDoseMillirems(float absorbedDoseMillirems);

	float getRadiationResistance();
	void setRadiationResistance(float radiationResistance);

	float getCurrentExposureMilliremsPerSecond();
	void setCurrentExposureMilliremsPerSecond(float currentExposureMilliremsPerSecond);

	float getLastExposureMilliremsPerSecond();
	void setLastExposureMilliremsPerSecond(float lastExposureMilliremsPerSecond);

	void increaseCurrentExposureMilliremsPerSecond(float currentExposureMilliremsPerSecond);
}
