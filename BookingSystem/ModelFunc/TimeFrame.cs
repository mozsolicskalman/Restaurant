using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.Models
{
    public partial class TimeFrame
    {
        public bool Contains(TimeFrame other)
        {
            if (other.IsCorrect() && this.IsCorrect())
            {
                return other.StartTime > this.StartTime &&
                         other.EndTime < this.EndTime;
            }
            return false;
        }

        public bool Overlaps(TimeFrame other)
        {
            if (other.IsCorrect() && this.IsCorrect())
            {
                return !(
                    (this.StartTime < other.StartTime && this.EndTime < other.EndTime) ||
                    (this.StartTime > other.StartTime && this.EndTime > other.EndTime)
                    );
            }
            return false;
        }

        public bool IsCorrect()
        {
            return StartTime < EndTime;
        }
    }
}
