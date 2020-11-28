using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.Models
{
    public partial class Seller
    {
        public bool IsAvailableIn(TimeFrame timeFrame)
        {
            foreach (var timeAvailable in WorkingHours)
            {
                if (timeAvailable.Contains(timeFrame))
                {
                    foreach (var appointment in Appointments)
                    {
                        if (appointment.Accepted && !appointment.TimeFrame.Overlaps(timeFrame))
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
